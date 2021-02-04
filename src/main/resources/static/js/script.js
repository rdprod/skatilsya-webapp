
//Функция обработки scroll на основной странице

$(document).ready( function() {
  $('.video > ul').scrollLeft(345);
});


//Функции обработки вывода текста в блоке about

$(document).scroll( function() {
  if ( $(document).scrollTop() > 130 )
  {
    setTimeout( function() {
      $('.js-change-opacity-1 > span').css({opacity: 1});
    }, 0);

    setTimeout( function() {
      $('.js-change-opacity-2 > span').css({opacity: 1});
    }, 500);

    setTimeout( function() {
      $('.js-change-opacity-3 > span').css({opacity: 1});}, 1000); 
  }
});

//Кнопка в блоке мерч
$('#merch-button').click( function(event) {
  alert("Скоро в продаже!");
});

//Ajax обработка-комметариев
$('.form-comment-container').submit(function(e) {
  
  e.preventDefault();

  $.ajax({
    type: "POST",
    url: "/feedback.html",
    data: $('.form-comment-container').serialize(),
    success: function(data) {
      $('.main-comment-area').prepend(`
        <div class="comment-area">
          <img src="/static/images/placeholder.png">
          <div class="comment-text">
            <h2>${ data.user }</h2>
            ${ data.text }
          </div>
        </div>
      `)  
      $('#id_text').val('');
      $('.not-comment').hide();
    } 
  });

});