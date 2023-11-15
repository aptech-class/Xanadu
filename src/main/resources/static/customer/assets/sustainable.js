/** Shopify CDN: Minification failed

Line 263:8 Transforming const to the configured target environment ("es5") is not supported yet
Line 297:4 Transforming let to the configured target environment ("es5") is not supported yet
Line 314:2 Transforming const to the configured target environment ("es5") is not supported yet
Line 315:2 Transforming const to the configured target environment ("es5") is not supported yet
Line 325:2 Transforming const to the configured target environment ("es5") is not supported yet
Line 330:2 Transforming const to the configured target environment ("es5") is not supported yet

**/
/*
All scripts within this file are for the sustainability part of the site. Scripts are divided by sections. 

TABLE OF CONTENTS (Search for section based on the names in the TOC below)
1. sustainable block
2. sustainable accordion + sustainable fluid grid
3. sustainable cards + sustainable grid
4. sustainable toggle
5. sustainable past
6. sustainable subnav
7. sustainable social
8. sustainable carousel
9. sustainable video modal
10. sustainable animate cards
11. sustainable steps
*/


$(document).ready(function() {

  // sustainable block
  var a = $('.text a.a')
  if ($(window).width() > 1000) {
    var vid = $('.block video')
    if (vid.length > 0) {
      vid.mouseover(function() {
        $(this)[0].play();
      }).mouseout(function(){
        $(this)[0].pause();
      })
    }
  } else {
    var vid = $('.block')
    if (vid.length > 0) {
      vid.mouseover(function() {
        $(this).find('video')[0].play();
      }).mouseout(function(){
        $(this).find('video')[0].pause();
      })
    }
  }
  if (a.length > 0) {
    a.mouseover(function() {
      $(this).parents().siblings('.asset').find('video')[0].play();
    }).mouseout(function(){
      $(this).parents().siblings('.asset').find('video')[0].pause();
    })
  }

  // sustainable accordion + sustainable fluid grid
  var block = $('.accordion .inner, .fluid .card')
  block.mouseover(function() {
    $(this).addClass('active');
    $(this).siblings().addClass('inactive');
  }).mouseout(function(){
    $(this).removeClass('active');
    $(this).siblings().removeClass('inactive');
  })

  // sustainable cards + sustainable grid
  var instance = $(".card-container, .grid-js, .slider-container, .video-modal");
  $.each( instance, function(key, value) {
    var arrows = $(instance[key]).find(".arrow"),
        prevArrow = arrows.filter('.arrow-prev'),
        nextArrow = arrows.filter('.arrow-next'),
        box = $(instance[key]).find(".cards"),
        x = 0,
        mx = 0,
        card = $(instance[key]).find('.card, .text.js'),
        x = $(instance[key]).find('.icon-close')

      if ($(box).length > 0) {
        var maxScrollWidth = box[0].scrollWidth - (box[0].clientWidth / 2) - (box.width() / 2)
      }

    $(card).mouseover(function() {
      var index = $(this).index() + 1;
      $('.hover-' + index).addClass('active');
      if ($(this).find('video').length > 0) {
        $(this).find('video')[0].play()
      }
    }).mouseout(function(){
      var index = $(this).index() + 1;
      $('.hover-' + index).removeClass('active');
      if ($(this).find('video').length > 0) {
        $(this).find('video')[0].pause()
      }
    });

    $(card).on('click', function(e) {
      if ($(e.target).hasClass('modal-btn')) {
        if ($(".modal").length > 0) {
          $('body, html').addClass('no-scroll');
          $('.back-to-top, .acsb-trigger').addClass('hide');
          var index = $(this).index() + 1, 
              el = $(this).parents().siblings('.modal-content');
          el.addClass('active');
          el.children('.modal-' + index).addClass('active');
          $('.image-container').addClass('active');
        }
      }
      if ($(e.target).hasClass('video-btn')) {
        if ($(".video-content").length > 0) {
          $('body, html').addClass('no-scroll');
          $('.back-to-top, .acsb-trigger').addClass('hide')
          var index = $(this).index() + 1;
          $('.video-content, .video-' + index).addClass('active');
          $('.video-' + index)[0].src += "&autoplay=1";
        }
      }
    });

    var stopAllYouTubeVideos = () => {
      var iframes = document.querySelectorAll('iframe');
      Array.prototype.forEach.call(iframes, iframe => { 
        iframe.contentWindow.postMessage(JSON.stringify({ event: 'command', 
      func: 'stopVideo' }), '*');
     });
    }
    var closeModal = () => { 
      $('body, html').removeClass('no-scroll');
      $('.modal-content, .modal, .video-content, .video, .image-container, .video-container, iframe').removeClass('active');
      $('.back-to-top, .acsb-trigger').removeClass('hide');
      stopAllYouTubeVideos();
    }

    $(x).on('click', function() {
      closeModal();
    });
    $(document).on('keydown', function (e) {
      if (e.keyCode === 27) { // esc
        closeModal();
      }
    });

    $(arrows).on('click', function() {
      if ($(this).hasClass("arrow-next")) {
        x = ((box.width() / 2)) + box.scrollLeft() - 10;
        box.animate({
          scrollLeft: x,
        })
      } else {
        x = ((box.width() / 2)) - box.scrollLeft() - 10;
        box.animate({
          scrollLeft: -x,
        })
      }  
    });
      
    $(box).on({
      mousemove: function(e) {
        var mx2 = e.pageX - this.offsetLeft;
        if(mx) this.scrollLeft = this.sx + mx - mx2;
      },
      mousedown: function(e) {
        this.sx = this.scrollLeft;
        mx = e.pageX - this.offsetLeft;
      },
      scroll: function() {
        toggleArrows();
      }
    });

    $(document).on("mouseup", function(){
      mx = 0;
    });
    
    function toggleArrows() {
      if(box.scrollLeft() > maxScrollWidth - 10) {
        // disable next button when right end has reached 
        nextArrow.addClass('disabled');
      } else if(box.scrollLeft() < 10) {
        // disable prev button when left end has reached 
        prevArrow.addClass('disabled');
      } else {
        // both are enabled
        nextArrow.removeClass('disabled');
        prevArrow.removeClass('disabled');
      }
    }
  });

  // sustainable toggle
  var span = $('.toggle-categories span')

  span.on('click', function() {
    $('.image-container img').removeClass('active');
    span.removeClass('active');
    $(this).addClass('active');

    var index = $(this).index() + 1;
    $('.image-' + index).addClass('active');
  });

  // sustainable past
  var card = $('.past .card'),
      filt = $('.past .filter span'),
      cards = $('.past .cards'),
      clear = $('.past .clear, .past .first')

  filt.on('click', function(e) {
    card.removeClass('active');
    filt.removeClass('active');
    clear.addClass('active');
    var cla = $(e.target).attr("class");
    if ($(this).hasClass(cla)) {
      $(this).addClass('active')
      cards.find("." + cla).addClass('active')
    }
  });
  clear.on('click', function(e) {
    card.addClass('active');
    filt.removeClass('active');
    clear.removeClass('active');
  });

  // sustainable subnav
  var links = $('.sustainable .subnav li'),
      subnav = $('.sustainable .subnav')

  if (!/Mobi|Tablet|iPad|iPhone/.test(navigator.userAgent)) {
    links.mouseover(function() {
      if ($(this).find('.child-links').length > 0) {
        $(this).find('.child-links').addClass('active');
        links.not(this).addClass('hide');
      }
    });
    subnav.mouseleave(function(){
      $(this).find('.child-links').removeClass('active');
      links.removeClass('hide');
    })
  }

  var rafTimer;
  window.onscroll = function (event) {
    cancelAnimationFrame(rafTimer);
    rafTimer = requestAnimationFrame(change);
  };
  function change() {
    if (window.scrollY > 1) {
      subnav.addClass('active');
    } else {
      subnav.removeClass('active');
    }
  }

  // sustainable social
  var container = document.getElementById('social-slide');
  if ($(container).length > 0) {
    var scrollWidth = container.scrollWidth;
    window.addEventListener('load', () => {
      self.setInterval(() => {
        const first = document.querySelector('#social-slide div');

       if(!isElementInViewport(first)){
          container.appendChild(first);
          container.scrollTo(container.scrollLeft - first.offsetWidth, 0);
        }
        if (container.scrollLeft !== scrollWidth) {
          container.scrollTo(container.scrollLeft + 1, 0);
        }
      }, 15);
    });

    function isElementInViewport (el) {
        var rect = el.getBoundingClientRect();
        return rect.right > 0;
    }
  }

  // sustainable carousel
  if ($('.slider-container').length > 0) {
    $('.sustainable .image-container').slick({
      dots: true,
      infinite: true,
      speed: 300,
      slidesToShow: 1,
      slidesToScroll: 1,
      nextArrow:$('.slider-next'),
      prevArrow:$('.slider-prev')
    });
  }

  // sustainable video modal
  var logo = $('.logos .modal-btn')
  logo.on('click', function(e) {
    let index = $(this).index() + 1,
        el = $(this).parents().parents().siblings('.asset');
    logo.removeClass('active');
    $(this).addClass('active');
    $('.video-modal, .video-container, .video-' + index).addClass('active');
    $('.video-' + index)[0].src += "&autoplay=1";
  });
  logo.mouseover(function() {
    logo.removeClass('active');
    $(this).addClass('active');
    var index = $(this).index() + 1,
        el = $(this).parents().parents().siblings('.asset');
    $('.asset img').removeClass('active');
    el.children('.image-' + index).addClass('active');
  })

  // sustainable animate cards
  const target = document.querySelector('.card-container.animate');
  const observerOptions = {
          root: null,
          rootMargin: "0px",
          threshold: [1, .5]
        };

  function handleIntersection(entries) {
    target.classList.toggle( 'active', entries[0].isIntersecting );
  }

  const observer = new IntersectionObserver(handleIntersection, observerOptions);

  observer.observe(target);

  // sustainable steps 
  const swiper = new Swiper('.swiper', {
    loop: true,
    spaceBetween: 30,
    slidesPerView: 1.25,
    centeredSlides: true,
    pagination: {
      clickable: true,
      el: '.swiper-pagination',
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    breakpoints: {
      1000: {
        slidesPerView:'auto',
        centeredSlides: false
      }
    },
  });

// end of doc
}) 