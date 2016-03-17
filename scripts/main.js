$( document ).ready(function() {
    console.log( "ready!" );
    $('.c-iconbar__link').on('click', function() {
        $('.c-iconbar__link').removeClass('is-active');
        $(this).addClass('is-active');
    });
});
