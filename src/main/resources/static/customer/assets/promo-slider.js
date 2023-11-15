/** Shopify CDN: Minification failed

Line 8:2 Transforming let to the configured target environment ("es5") is not supported yet
Line 9:2 Transforming const to the configured target environment ("es5") is not supported yet

**/
document.addEventListener('DOMContentLoaded', () => {
  let slider_speed = document.querySelector('.promo-bar__message').dataset.slideTime;
  const promoSlider = new Swiper('.promo-bar__message', {
    slidesPerView: 1,
    loop: true,
    speed: 500,
    grabCursor: true,
    effect: 'fade',
    preventClicks: false,
    preventClicksPropagation: false,
    fadeEffect: {
      crossFade: true
    },
    autoplay: {
      delay: slider_speed,
    }
  });
 
});
