let win_href = window.location.href;//페이지 전체 경로
let win_path = window.location.pathname;//페이지 이름 경로
let win_search = window.location.search;//페이지 ?a=b에 대한 경로

function pageReload(){
    location.reload(true);
}

/*나중에 활성화 필수(url로 직접적인 접근하는 것을 막아줌*/
//$(document).ready(function (){
//    if (document.referrer == '' || document.referrer == null){
//        standbyShow('접근 오류','잘못된 방식으로 접근하셨습니다.');
//        setTimeout(function(){
//            document.location.href = '/';
//        },1000);
//    }
//});