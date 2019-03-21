/**
 * Created by Administrator on 2018/1/19 0019.
 */
$(function(){
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        slidesPerView: 1,
        paginationClickable: true,
        spaceBetween:0,
        autoplay: 2500,
        loop: true
    });
});