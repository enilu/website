  /**
    * isMobile
    * responsiveMenu
    * headerFixed 
    * ajaxContactForm
    * alertBox
    * blog_slider
    * detectViewport
    * flatIconboxCarousel
    * blogCarousel
    * flatClient
    * googleMap
    * videoPopup
    * testimonialSlide
    * onepage_nav
    * Comment-respond 
    * flatClient
    * responsiveVideo
    * swClick
    * goTop
    * toggleExtramenu
    * retinaLogos
    * parallax
    * popupGallery
    * removePreloader
    * flatSearch
    * flatAccordion
    * flatCountdown
    * portfolioIsotope
    * projectSingle
    * flatFilterPrice
    * tabs
    * flatCarousel
    * flatClient
    * counter
    * flatServicesCarouselv1
    * flatServicesCarouselv2
    * flatServicesCarouselArrow
    * flatServicesCarouselArrowThin
    * flatServicesCarouselv3
    * flatServicesCarouselv4
    * toggleExtramenu
   */

;(function($) {

   'use strict'





    var headerFixed = function() {
        if ( $('body').hasClass('header_sticky') ) {
            var nav = $('.header');
            if ( nav.size() != 0 ) {
                var offsetTop = $('.header').offset().top,
                    headerHeight = $('.header').height(),
                    injectSpace = $('<div />', { height: headerHeight }).insertAfter(nav);   
                    injectSpace.hide();  
                $(window).on('load scroll', function(){
                    if ( $(window).scrollTop() > offsetTop + 120 ) {
                        nav.addClass('downscrolled');
                        injectSpace.show();
                    } else {
                        nav.removeClass('header-small downscrolled');
                        injectSpace.hide();
                    }

                    if ( $(window).scrollTop() > 500 ) {
                        nav.addClass('header-small upscrolled');
                    } else {
                        nav.removeClass('upscrolled');
                    }
                })
            }
        }     
    };
  

   

    var detectViewport = function() {
        $('[data-waypoint-active="yes"]').waypoint(function() {
            $(this).trigger('on-appear');
        }, { offset: '90%', triggerOnce: true });

        $(window).on('load', function() {
            setTimeout(function() {
                $.waypoints('refresh');
            }, 100);
        });
    };   
   

    var flatClient = function() {
        $('.flat-row').each(function() {            
            if ( $().owlCarousel ) {
                $(this).find('.flat-client').owlCarousel({
                    loop: true,
                    margin: $('.flat-client').data('margin'),
                    nav: $('.flat-client').data('nav'),
                    dots: $('.flat-client').data('dots'),                     
                    autoplay: $('.flat-client').data('auto'),                    
                    responsive:{
                        0:{
                            items: 1
                        },
                        480:{
                            items: next_item($(this).data('item')-1)
                        },
                        767:{
                            items: next_item($(this).data('item'))
                        },
                       
                        1200: {
                            items: $('.flat-client').data('item')
                        }
                    }
                });
            }
        });
    };

    function next_item($item) {
        var $int_item = parseInt($item);
        if ( $int_item -1 > 0) {
            return $int_item -1;
        }
        else {
            return 1;
        }
    }
   
   
    var videoPopup =  function() {
        $(".fancybox").on("click", function(){
            $.fancybox({
              href: this.href,
              type: $(this).data("type")
            }); // fancybox
            return false   
        }); // on
    }

    var testimonialSlide = function() {
        $('.flat-testimonials-slider').each(function(){
            $(this).children('#flat-testimonials-carousel').flexslider({
                animation: "slide",
                controlNav: false,
                animationLoop: false,
                slideshow: false,
                itemWidth: 70,
                itemMargin: 10,
                asNavFor: $(this).children('#flat-testimonials-flexslider')
            });
            $(this).children('#flat-testimonials-flexslider').flexslider({
                animation: "slide",
                controlNav: false,
                animationLoop: false,
                slideshow: false,                
                sync: $(this).children('#flat-testimonials-carousel'),
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });
        });
    };  

    var onepage_nav = function () {
        $('.mainnav > ul > li > a').on('click',function() {           
            var anchor = $(this).attr('href').split('#')[1];
            var largeScreen = matchMedia('only screen and (min-width: 992px)').matches;
            var headerHeight = 0;
            headerHeight = $('.header').height();
            if ( anchor ) {
                if ( $('#'+anchor).length > 0 ) {
                   if ( $('.upscrolled').length > 0 && largeScreen ) {
                        headerHeight = headerHeight;
                   } else {
                        headerHeight = 0;
                   }                   
                   var target = $('#'+anchor).offset().top - headerHeight;
                   $('html,body').animate({scrollTop: target}, 1000, 'easeInOutExpo');
                }
            }
            return false;
        })

        $('.mainnav ul > li > a').on( 'click', function() {
            $( this ).addClass('active').parent().siblings().children().removeClass('active');
        });
    }  


    var swClick = function () {
        function activeLayout () {
            $(".switcher-container" ).on( "click", "a.sw-light", function() {
                $(this).toggleClass( "active" );
                $('body').addClass('首页-boxed');  
                $('body').css({'background': '#f6f6f6' });                
                $('.sw-pattern.pattern').css ({ "top": "100%", "opacity": 1, "z-index": "10"});
            }).on( "click", "a.sw-dark", function() {
                $('.sw-pattern.pattern').css ({ "top": "98%", "opacity": 0, "z-index": "-1"});
                $(this).removeClass('active').addClass('active');
                $('body').removeClass('首页-boxed');
                $('body').css({'background': '#fff' });
                return false;
            })       
        }        

        function activePattern () {
            $('.sw-pattern').on('click', function () {
                $('.sw-pattern.pattern a').removeClass('current');
                $(this).addClass('current');
                $('body').css({'background': 'url("' + $(this).data('image') + '")', 'background-size' : '30px 30px', 'background-repeat': 'repeat' });
                return false
            })
        }

        activeLayout(); 
        activePattern();
    } 


    var flatAccordion = function() {
        var args = {duration: 600};
        $('.flat-toggle .toggle-title.active').siblings('.toggle-content').show();

        $('.flat-toggle.enable .toggle-title').on('click', function() {
            $(this).closest('.flat-toggle').find('.toggle-content').slideToggle(args);
            $(this).toggleClass('active');
        }); // toggle 

        $('.flat-accordion .toggle-title').on('click', function () {
            if( !$(this).is('.active') ) {
                $(this).closest('.flat-accordion').find('.toggle-title.active').toggleClass('active').next().slideToggle(args);
                $(this).toggleClass('active');
                $(this).next().slideToggle(args);
            } else {
                $(this).toggleClass('active');
                $(this).next().slideToggle(args);
            }     
        }); // accordion
    }; 

    var flatCountdown = function() {
        var anycar_style = function(data) {
         $(this.el).html(
            "<div class='square days'>" +                
                "<div class='numb'>" + this.leadingZeros(data.days, 2) + "</div>" +
                "<div class='text'>Days</div>" +
            "</div>" +
            "<div class='square hours'>" +                
                "<div class='numb'>" + this.leadingZeros(data.hours, 2) + "</div>" +
                "<div class='text'>Hours</div>" +
            "</div>" +
            "<div class='square mins'>" +                
                "<div class='numb'>" + this.leadingZeros(data.min, 2) + "</div>" +
                "<div class='text'>Minutes</div>" +
            "</div>" +
            "<div class='square secs'>" +                
                "<div class='numb'>" + this.leadingZeros(data.sec, 2) + "</div>" +
                "<div class='text'>Seconds</div>" +
            "</div>");
        }

        $('.countdown').each(function() {
            $(this).countdown({
                date: $(this).attr('data-date'),
                render: anycar_style
            });
        });
    };

    var portfolioIsotope = function() {         
        if ( $().isotope ) {           
            var $container = $('.build-portfolio');
            $container.imagesLoaded(function(){
                $container.isotope({
                    itemSelector: '.items',
                    transitionDuration: '1s',
                    percentPosition: true,               
                    masonry: {
                    columnWidth: '.grid-sizer'
                }
                });
            });

            $('.portfolio-filter li').on('click',function() {                           
                var selector = $(this).find("a").attr('data-filter');
                $('.portfolio-filter li').removeClass('active');
                $(this).addClass('active');
                $container.isotope({ filter: selector });
                return false;
            });            
        };

        if ($().fancybox) {
            $(".popup-gallery").fancybox({
                'transitionIn'      : 'none',
                'transitionOut'     : 'none',
                'titlePosition'     : 'over',
                'titleFormat'       : function(title, currentArray, currentIndex, currentOpts) {
                    return '<span id="fancybox-title-over">Image ' +  (currentIndex + 1) + ' / ' + currentArray.length + ' ' + title + '</span>';
                }
            });
        }
    };

    var projectSingle = function() {
        $('.flat-gallery-slider').each(function() { 
            var thumbnail = $(this).data('thumbnail');
            var show_nav = $(this).data('show_nav');
            $(this).children('#flat-gallery-carousel').flexslider({
                animation: "slide",
                controlNav: false,
                directionNav:  true,
                animationLoop: true,
                slideshow: true,
                itemWidth:thumbnail,
                itemMargin: 30,
                prevText       :  '<i class="fa fa-chevron-left"></i>',
                nextText       :  '<i class="fa fa-chevron-right"></i>',
                asNavFor: $(this).children('#flat-gallery-flexslider')
            });
            $(this).children('#flat-gallery-flexslider').flexslider({
                animation: "slide",
                controlNav: false,
                directionNav   :  show_nav,
                animationLoop: true,
                slideshow: true,                
                sync: $(this).children('#flat-gallery-carousel'),
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });
        });
    };  

    var flatFilterPrice = function() {
        if( $().slider ) {
            $( ".price_slider" ).slider({
                range: true,
                min: 9,
                max: 35,
                values: [ 9, 35 ],
                slide: function( event, ui ) {
                    $( ".price_label > input " ).val( "$" + ui.values[ 0 ] + "  - £" + ui.values[ 1 ] );
                    }
            });

            $( ".price_label > input " ).val( "$" + $( ".price_slider" ).slider( "values", 0 ) +
            "  -  $" + $( ".price_slider" ).slider( "values", 1 ) );
            $( ".ui-slider-handle").append("<span class='shadow'></span>");
        }
    };

    <div class="rating clearfix">
        <div class="ratings">
            <div class="rating-box">
                <div style="width:60%" class="rating"></div>
            </div>
        </div> <!-- /.ratings -->
    </div>




    var counter = function() {
        $('.flat-counter').on('on-appear', function() {

            $(this).find('.numb-count').each(function() {
                var to = parseFloat($(this).attr('data-to')), speed = parseFloat($(this).attr('data-speed'));
                if ( $().countTo ) {
                    $(this).countTo({
                        to: to,
                        speen: speed
                    });
                }
            });
        }); //counter
    };


    var layout = function() {
        $(".list-grid a").on("click",function(e) {
            e.preventDefault();
            $(".list-grid a").removeClass("active");
            $(this).addClass("active");
            $("body").removeClass("shop-grid shop-list").addClass($(this).data("layout"));
        })
    }
    
                            
   	// Dom Ready
	$(function() { 
        if ( matchMedia( 'only screen and (min-width: 991px)' ).matches ) {
        }  
        responsiveMenu();   
        headerFixed();
        counter();
        parallax();
        flatSearch();
        flatAccordion();
        portfolioIsotope();
        projectSingle();
        flatFilterPrice ();
        tabs();
        flatServicesCarouselv1();
        flatServicesCarouselv2();
        flatServicesCarouselv3();
        flatServicesCarouselv4();
        flatServicesCarouselArrow();
        flatServicesCarouselArrowThin();
        ajaxContactForm();
        alertBox();
        videoPopup();
        flatCarousel();
        flatClient();
        googleMap();
        toggleExtramenu();
        layout();
        detectViewport();
        removePreloader ();
        goTop();
        flatCountdown();

   	});

})(jQuery);