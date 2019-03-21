/**
 * Created by Administrator on 2017/12/26 0026.
 */
$(function () {
        $(".modify_btn").click(function() {
            // 显示遮罩层
            var mask = $(".dialog-mask").css("display", "block");
            setTimeout(function() {
                mask.css("opacity", "1");
            }, 10);

            // 判断要显示哪一个 对话框
            var type = $(this).attr("data-type");
            var show = $(".dialog-child[data-type=" + type + "]");
            show.css("display", "block");
        });

        $(".dialog-close").click(function() {
            // 隐藏遮罩层
            var mask = $(".dialog-mask").css("opacity", "0");
            setTimeout(function() {
                mask.css("display", "none")
            }, 300);
            // 隐藏所有child
            $(".dialog-child").css("display", "none");
        });
});


