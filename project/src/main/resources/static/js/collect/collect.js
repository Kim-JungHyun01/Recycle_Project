$(document).ready(function() {
    $('.item-btn').on('click', function() {
        $('.item-btn').removeClass('bg-success text-white');
        $(this).addClass('bg-success text-white');
        $('.select-btn').text($(this).text() + ' 선택됨');
    });
});