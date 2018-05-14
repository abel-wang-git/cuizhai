layui.define(['element', 'layer', 'util', 'form'], function (exports) {
    var $ = layui.jquery;
    var element = layui.element;
    var layer = layui.layer;
    var util = layui.util;
    var form = layui.form
    form.on('select(address)', function (data) {
        var s = $('input[name="customerAddress"]');
        var str = s.val().split("%");
        if (str.length > 3) {
            str.splice(3, 1)
        }
        if (data.elem.id === "province") {
            str[0] = data.value;
        }
        if (data.elem.id === "city") {
            str[1] = data.value;
        }
        if (data.elem.id === "area") {
            str[2] = data.value;
        }

        s.val(str.join("%") + '%');

        console.log(str)
    });
})