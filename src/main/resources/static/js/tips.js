function isdel(one, two) {
    layer.confirm('确定删除该文章？',
        function () {
            $.ajax({
                url: "/blog/del/" + one + "/" + two,
                type: "GET",
                success: function (data) {
                    $("#blogcenter").html(data)
                }
            });
            layer.msg('删除成功', {icon: 1});
            return true;
        });
}

function isCancelCollect(one, two) {

    layer.confirm('确定取消收藏？',
        function () {
            $.ajax({
                url: "/blog/cancelcollect/" + one + "/" + two,
                type: "GET",
                success: function (data) {
                    $("#collectcenter").html(data)
                }
            });
            layer.msg('o的k', {icon: 1});
            return true;
        });
}


function isCancenFollow(one, two) {

    layer.confirm('确定取消关注？',
        function () {
            $.ajax({
                url: "/blog/cancelfollow/" + one + "/" + two,
                type: "GET",
                success: function (data) {
                    $("#followcenter").html(data)
                }
            });
            layer.msg('o的k', {icon: 1});
            return true;
        });
}


function isaddFollow(one, two, o) {
    $.ajax({
        url: "/blog/addfollow/" + one + "/" + two,
        type: "GET",
        success: function (data) {
            layer.msg('已关注', {icon: 1});
        }
    }).done(function () {
        isf(one, two, $(o).parent());
    });

}

//是否关注
function isf(uid, fid, obj) {
    $.ajax({
        url: "/follow/isfollow/" + uid + "/" + fid,
        type: "GET",
        success: function (data) {
            if (data == 200) {
                $(obj).find(".isf").text("已关注");
            } else if (data == 100) {
                $(obj).find(".isf").text("关注");
            }
        }
    })

}