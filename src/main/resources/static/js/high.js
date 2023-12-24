function high(one) {

    $("#highler").children("div").each(function (index,value) {
        var text = $(value).find(".titlecolor").text();
        var content = $(value).find(".contcolor").text();
        var a = text.match(new RegExp(one,'ig'));
        var b = content.match(new RegExp(one,'ig'));
        text= text.replace(a,"<strong style='color: #007bff;'>"+a+"</strong>");
        content= content.replace(b,"<strong style='color: #007bff;'>"+b+"</strong>");
        $(value).find(".showtitlecolor").html(text);
        $(value).find(".showcontcolor").html(content);

        $("#loadd").hide();
    });
}

