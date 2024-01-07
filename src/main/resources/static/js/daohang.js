$(function () {
    var menus = document.querySelectorAll('.menu');
    menus.forEach(function (menu) {
        var menuItems = menu.querySelectorAll('.menu-item');
        menuItems.forEach(function (item) {
            item.addEventListener('click', function (e) {
                menuItems.forEach(function (item) {
                    return item.classList.remove('menu_item_active', 'menu-item_no_hover');
                });
                var target = e.target;
                target.classList.add('menu_item_active', 'menu-item_no_hover');
            });
        });
    });
})

