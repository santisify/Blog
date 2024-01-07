Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

$(document).ready(function () {
    var user = $("#uname").text();
    var uid = $("#uid").val();
    var avatar = $("#useravatar").attr("src");
    // 指定websocket路径
    var websocket;
    var isclose = false;//是否关闭
    var timeout = 30 * 1000;//心跳时间30s
    var recontime = 10000;//重连时间间隔
    var lockReconnect = false;//避免重复连接
    var hearttimeout;//心跳倒计时
    var heartservertimeout;//心跳超时时间
    var reconntime; //重连时间
    //连接
    function initwebsocket() {
        if ('WebSocket' in window) {
            console.log("初始连接00");
            websocket = new WebSocket("ws://localhost:81/chat/webSocket/" + uid);
        }
    }

    initwebsocket();

    websocket.onopen = function () {
        websocketheart();
        console.log("连接打开")
    };

    websocket.onmessage = function (event) {

        var data = JSON.parse(event.data);
        switch (data.type) {
            case 'heartbeat':
                resetheart();
                break;
            case 'msg':
                // 普通消息
                // 接收服务端的实时消息并添加到HTML页面中
                //未读消息数增加
                var span = $(".u-list").find("input[value=" + data.from + "]").parent("div").find(".tips");
                var c = span.text();
                if (!$(span).parent("div").parent("div").hasClass("active-msg-item")) {
                    c++;
                    if (c > 0) {
                        $(span).css("visibility", "visible");
                        $(span).text(c);
                        $(span).parent("div").parent("div").find(".last-msg").find("span").text(data.text);
                    }
                    if (c > 99) {
                        $(span).text("99+");
                        $(span).parent("div").parent("div").find(".last-msg").find("span").text(data.text);
                    }
                }
                if ($(".chatuserid").val() == data.from) {
                    $(".msg-content").append("<div class='msg-item1 msg-item-left'><img class='' src=" + data.avatar + " ><div class='msg'>" + data.text + "</div></div>");
                    $(".active-msg-item .last-msg span").text(data.text);
                    changeRead(data);
                    // 滚动条滚动到最低部
                    scrollToBottom();
                }
        }
    };

    websocket.onclose = function () {
        console.log("连接关闭")
        isclose = true;
        websocket.close();
    };

    websocket.onerror = function (event) {
        console.log("websocket连接错误" + event)
    };

    //重连
    function reconnect() {
        if (lockReconnect) {
            return;
        }
        lockReconnect = true;
        console.log("开始重连");
        reconntime && clearTimeout(reconntime);
        reconntime = setTimeout(function () {
            initwebsocket();
            lockReconnect = false;
        }, reconntime)
    }

    //重置心跳
    function resetheart() {
        console.log("重置心跳")
        //清除时间
        clearTimeout(hearttimeout);
        clearTimeout(heartservertimeout);
        websocketheart();
    }

    //心跳
    function websocketheart() {
        hearttimeout && clearTimeout(hearttimeout);
        heartservertimeout && clearTimeout(heartservertimeout);
        console.log("开启心跳")
        hearttimeout = setTimeout(function () {
            //发送一个心跳
            if (websocket && websocket.readyState == 1) {
                sendheart();
            } else {
                reconnect();
            }
            heartservertimeout = setTimeout(function () {
                console.log("心跳超时")
                websocket.close();
            }, timeout);
        }, timeout)
    }

    function closetab() {
        $(".msg-content").append("<div class='msg-tab msg-item1'>连接断开，请刷新重试</div>");
        scrollToBottom();
    };

    function flushuser(fun) {
        //获取登录用户的所有关注用户
        $.post("/chat/onlineusers?currentuser=" + uid, function (data) {
            $("#ulist").html($(data).children("div"));
        }).done(function () {
            $("#loadd").hide();
            fun();
        });
    };

    //发送心跳文本
    function sendheart() {
        var data = {};
        data["from"] = uid;
        data["to"] = uid;
        data["type"] = "heartbeat";
        websocket.send(JSON.stringify(data));
    }

    flushuser(ischat);

    $("#send").click(function () {
        var data = {};
        data["from"] = uid;
        data["to"] = $("body").data("to");
        data["text"] = $("#messagetext").val();
        data["type"] = "msg";
        data["avatar"] = $("#useravatar").attr("src");
        websocket.send(JSON.stringify(data));
        $(".msg-content").append("<div class='msg-item1 msg-item-right'><img class='' src=" + avatar + " ><div class='msg'>" + $("#messagetext").val() + "</div></div>");
        if (isclose == true) {
            closetab();
        }
        scrollToBottom();
        $(".active-msg-item .last-msg span").text($("#messagetext").val());
        $("#messagetext").val("");
    });

    $("#ulist div").each(function () {
        if ($(this).hasClass("active-msg-item")) {
            $(this).click(function () {
                return false;
            })
        }
    })

});

function changeRead(data) {
    var msgid = data.msgid;
    $.post("/chat/changemsgstatus?id=" + data.msgid)
}


//判断是否是是直连聊天
function ischat() {
    var id = $("#isuid").val();
    var inputs = $("#ulist").find("input");
    $("#ulist").find("input").each(function (i, input) {
        if (input.value == id) {
            var div = $(input).parent("div");
            talk(div);
        }
    });

}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        if ($("#messagetext").val().trim() == "") {
            return false;
        } else {
            $('#send').triggerHandler('click');
            event.returnValue = false;
            return false;
        }
    }
});
var len = 1;
var ishasdata = true;

//监听滚动条高度
function linsterscroll(ruid, suid) {
    var div = document.getElementById("msgarea");
    var l = 10;
    div.onscroll = function () {
        var sh = div.scrollHeight;
        var st = div.scrollTop;
        var ch = div.clientHeight;
        if (st + ch === sh) {
            if (len === 1) {
                return;
            }
        } else if (st === 0) {
            // l=ch-st;
            console.log(l)
            if (ishasdata === true) {
                len = len + 1;
                preload(ruid, suid);
                div.scrollTop = l;
            } else if (ishasdata === false) {
                return;
            }

        }
    }
}


//选择聊天对象后将对象名称赋值给data对象的to属性
function talk(a) {
    $("#chatshow").hide();
    $("#chatrelshow").show();
    $(a).find(".tips").css("visibility", "hidden");
    $(a).find(".tips").text("0");
    $("#messagetext").focus();
    $(".msg-content div").remove();
    $(".uname").text($(a).find(".who-name").text());
    $(".chatuserid").attr("value", $(a).find("input").val());
    $("body").data("to", $(a).find("input").val());
    $(a).siblings("div").removeClass("active-msg-item");
    $(a).addClass("active-msg-item");
    var ruid = $(a).find("input").val();
    var suid = $("#uid").val();
    loadmsg(ruid, suid)
    linsterscroll(ruid, suid);
}

function loadmsg(ruid, suid) {
    $.post(
        "/chat/msgstore?ruid=" + ruid + "&suid=" + suid + "&page=" + len, function (data) {
            $(data).each(function (index, item) {
                if (item.senderUid == $("#uid").val()) {
                    $(".msg-content").append("<div class='msg-item1 msg-item-right'><img class='' src=" + $("#useravatar").attr("src") + " ><div class='msg'>" + item.msgContent + "</div></div>");
                } else {
                    $(".msg-content").append("<div class='msg-item1 msg-item-left'><img class='' src=" + $(".active-msg-item img").attr("src") + " ><div class='msg'>" + item.msgContent + "</div></div>");
                }
            });
            scrollToBottom();
        }
    );
}

function preload(ruid, suid) {
    var msg = document.createElement("bg");
    $.post(
        "/chat/msgstore?ruid=" + ruid + "&suid=" + suid + "&page=" + len, function (data) {
            console.log(data.length)
            if (data.length == 0) {
                ishasdata = false;
            } else {
                $(data).each(function (index, item) {
                    if (item.senderUid == $("#uid").val()) {
                        $(msg).append("<div class='msg-item1 msg-item-right'><img class='' src=" + $("#useravatar").attr("src") + " ><div class='msg'>" + item.msgContent + "</div></div>");
                    } else {
                        $(msg).append("<div class='msg-item1 msg-item-left'><img class='' src=" + $(".active-msg-item img").attr("src") + " ><div class='msg'>" + item.msgContent + "</div></div>");
                    }
                });
            }
        }
    );
    $(msg).prependTo(".msg-content");
}

function scrollToBottom() {
    var div = document.getElementById("msgarea");
    div.scrollTop = div.scrollHeight;
}