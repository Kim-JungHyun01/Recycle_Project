$(document).ready(function(){
    //상세보기 페이지들에 css 링크 추가
    if(win_href.includes('/delete')
    ){
        $('head').prepend('<link rel="stylesheet" href="/css/common/detail.css">');
    }
});