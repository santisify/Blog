//添加评论
function toComment() {

    var bid = $("input[name='bid']").val();
    var uid = $("input[name='uid']").val();

    var userId = $("input[name='userId']").val();
    var userName = $("input[name='userName']").val();
    var userAvatar = $("input[name='userAvatar']").val();
    var topicId = $("input[name='topicId']").val();
    var content = $("textarea[name='content']").val();

    if (content.trim()==""){
        banktips();
        return false;
    }

    var params = {"userId":userId,"userName":userName,"userAvatar":userAvatar,"topicId":topicId,"content":content};

    //评论栏置空
    $("textarea[name='content']").val(" ");
    $("textarea[name='content']").focus();
    //提交异步请求

    $.post(
        "/blog/comment/"+bid+"/"+uid,
        params,
        function (data) {
            //赋值给对应标签div
            $("#refalsh").html(data);

        }
    ).done(function () {//异步完成后执行done
        //刷新评论时间
        flashtime();

    });
}

//删除评论
function delComment(obj) {

    var bid = $("input[name='bid']").val();
    var cid = $(obj).eq(0).children("input").val();

    $.ajax({
        url:"/blog/comment/delete/"+bid+"/"+cid,
        type:"GET",
        success:function (data) {
            $("#refalsh").html(data);
            tips();
        }
    }).done(function () {//异步完成后执行done
        //刷新评论时间
        flashtime();

    });
}

//添加关注
function addFollow(uid,fid) {

    $.ajax({
        url:"/follow/"+uid+"/"+fid,
        type:"GET",
        success:function (data) {
            $("#artcount").html(data);
            $("#follow1").hide();
            $("#follow2").show();
            $("#follow2").css("margin-top","0rem");
        }
    });
}

//取消关注
function cancelFollow(uid,fid) {

    $.ajax({
        url:"/follow/cancel/"+uid+"/"+fid,
        type:"GET",
        success:function (data) {
            $("#artcount").html(data);
            $("#follow2").hide();
            $("#follow1").show();
        }
    });
}

//是否关注
function isFollow(uid,fid) {

    $.ajax({
        url:"/follow/isfollow/"+uid+"/"+fid,
        type:"GET",
        success:function (data) {
            if (data==200){
                $("#follow2").show();
            }else if (data==100){
                $("#follow1").show();
            }
        }
    })
}

//添加收藏
function toCollect(uid,articleId,bid) {

    var param = {"uid":uid,"articleId":articleId,"bid":bid};

    $.post(
        "/blog/collect",
        param,
        function (data) {
            if (data==200){
                isCollect(uid,bid);
            }
        }
    )

}

//移除收藏
function removeCollect(uid,bid) {

    $.ajax({
        url:"/blog/recollect/"+uid+"/"+bid,
        type:"GET",
        success:function (data) {
            if (data==200){
                isCollect(uid,bid);
            }
        }
    })

}

//是否收藏
function isCollect(uid,bid) {

    $.ajax({
        url:"/blog/iscollect/"+uid+"/"+bid,
        type:"GET",
        success:function (data) {
            $("#iscollect").html(data);
        }
    })
}


function flashtime() {
    $(".time").timeago();
}

function tips() {

    // //提示
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg('删除成功',{offset:'70%'});
    });
}

function banktips() {

    // //提示
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg('请输入内容');
    });
}


