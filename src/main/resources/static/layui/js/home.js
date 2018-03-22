
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
                element.tabAdd('tab', { title: title, content: '<iframe src="' + url + '" style="position: absolute;height: 100%;width: 100%;border: none;"></iframe>', id: id });
                //切换到指定索引的卡片
                element.tabChange('tab', id);
            }, 500);
        }
    })

    $('#chang').on('click', function(){
        layer.open({
            type:1
            , title: '修改密码'
            , area: ['500px', '350px']
            , content:$("#changPwd")
        })
    });

    form.on('submit(user)', function(data){
/*        */
        if(data.field['passWd'].length<6){
            $("#pas").text("密码必须大于六位");
        }else if(data.field['passWd']!=data.field['rePassWd']){
            $("#tig").text("两次输入的密码不一致");
        }else {
            $.ajax({
                url:"changpwd",
                type:"post",
                dataType:'json',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(data.field)
            });
        }
        return false;
    });
    exports('home', {});
});
