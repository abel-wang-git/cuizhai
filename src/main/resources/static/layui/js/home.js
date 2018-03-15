layui.define(['element', 'layer', 'util', 'form'], function (exports) {
    var $ = layui.jquery;
    var element = layui.element;
    var layer = layui.layer;
    var util = layui.util;
    var form = layui.form
    element.on('nav(leftnav)', function (elem) {
        var url = $(elem).children('a').attr('data-url');   //页面url
        var id = $(elem).children('a').attr('data-id');     //tab唯一Id
        var title = $(elem).children('a').text();
        if (url == undefined) return;
        var tabTitleDiv = $('.layui-tab[lay-filter=\'tab\']').children('.layui-tab-title');

        var exist = tabTitleDiv.find('li[lay-id=' + id + ']');

        if (exist.length > 0) {
            //切换到指定索引的卡片
            element.tabChange('tab', id);
        } else {
            var index = layer.load(1);
            //由于Ajax调用本地静态页面存在跨域问题，这里用iframe
            setTimeout(function () {
                //模拟菜单加载
                layer.close(index);
                element.tabAdd('tab', { title: title, content: '<iframe src="' + url + '" style="width:100%;height:100%;border:none;outline:none;"></iframe>', id: id });
                //切换到指定索引的卡片
                element.tabChange('tab', id);
            }, 500);
        }
    })
    exports('home', {});
});