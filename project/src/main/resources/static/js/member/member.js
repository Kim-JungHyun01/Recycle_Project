if(win_href.includes('/signup')
|| win_href.includes('/mypage')){
        $('head').append('<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>');
}

//유효성코드 모음
//이름
let korEng = /^[가-힣ㄱ-ㅎA-Za-z]{2,10}$/;
//아이디
let checkId = /^[A-Za-z0-9]{5,12}$/;
//비밀번호
let checkPw = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+~`\-={}[\]:;"'<>,.?/\\]).{8,16}$/;
//전화번호
let checkTel = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

/*아이디 중복 확인*/
function idChk(){
    var id = $('#id').val();
    if(id == null || id == ""){
        alertShow('아이디 오류', '아이디를 입력하세요.');
        return false;
    }
    else if(!checkId.test(id)){
        alertShow('아이디 오류', '아이디는 영어, 숫자로만 5자 이상 15자 이하여야 합니다.');
        return false;
    }
    else{
        $.ajax({
            type: "post",
            url: "/memberChk",
            async: true,
            data: {"id":id},
            success:function(data){
                $('#id_none').hide();
                if(data == null || data == "null" || data == ""){
                    $('#id_ok').show();
                    $('#idcheck').val("ok");
                }
                else{
                    $('#id_no').show();
                    $('#idcheck').val("no");
                }
            },
            error:function(){
                alertShow('에러','아이디를 다시 입력해주세요');
            }
        });
    };
};
/*아이디 입력 시 중복확인 리셋*/
function idChkReset(){
    $('#idcheck').attr('value', 'no');
    $('#id_ok').hide();
    $('#id_none').show();
}
/*비밀번호 타입 변경*/
function pwTxtPw(ths){
    var pw_id = ths.dataset.id;
    var pw_type = $('#'+pw_id).attr('type');
    if(pw_type == 'password'){
        $('#'+pw_id).attr('type', 'text');
        $('#'+pw_id).next('button').find('img').attr('src', '/img/icon/common/eye_close.png');
    }
    else{
        $('#'+pw_id).attr('type', 'password');
        $('#'+pw_id).next('button').find('img').attr('src', '/img/icon/common/eye_open.png');
    }
}
/*비밀번호 유효성 검사*/
/*비밀번호 일치 여부*/
function pwChange(){
    let pw_val = $('#pw').val();
    if(checkPw.test(pw_val)){
        $('#pwchk1').hide();
        if(checkPw.test(pw_val) &&
        $('#pw').val() == $('#pw2').val()){
            $('#pwchk2').hide();
            $("#pwcheck").attr('value', "ok");
        }
        else{
            $('#pwchk2').show();
            $("#pwcheck").attr('value', "no");
        }
    }
    else{
        $('#pwchk1').show();
        $("#pwcheck").attr('value', "no");
    }
}
/*이름(한글, 영어만 가능)*/
function nameChk(){
    let name_val = $('#name').val();
    if(korEng.test(name_val)){
        $('#namechk').hide();
        $("#namecheck").attr('value', "ok");
    }
    else{
        $('#namechk').show();
        $("#namecheck").attr('value', "no");
    }
}
/*주소*/
//주소 API CDN 방식 사용
function execDaumPostcode(){
    new daum.Postcode({
        oncomplete: function(data){
            // 팝업을 통한 검색 결과 항목 클릭 시 실행
            var addr = ''; // 주소_결과값이 없을 경우 공백
            var extraAddr = ''; // 참고항목
            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R'){ // 도로명 주소를 선택
                addr = data.roadAddress;
            } else{ // 지번 주소를 선택
                addr = data.jibunAddress;
            }
            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            } else{
                document.getElementById("streetaddr").value = '';
            }
            // 선택된 우편번호와 주소 정보를 input 박스에 넣는다.
            document.getElementById('addr').value = data.zonecode;
            document.getElementById("streetaddr").value = addr;
            document.getElementById("streetaddr").value += extraAddr;
            // 우편번호 + 주소 입력이 완료되었음으로 상세주소로 포커스 이동
            document.getElementById("detailaddr").focus();
            $("#detailaddr").prop('readonly', false);
        }
    }).open();
}
function detailAddrWrite(){
    let addr_val = $('#addr').val();
    let streetaddr_val = $('#streetaddr').val();
    let detailaddr_val = $('#detailaddr').val();
    if(addr_val != null
    && streetaddr_val != null
    && detailaddr_val != null){
        $("#addresscheck").attr('value', "ok");
    }
    else{
        $("#addresscheck").attr('value', "no");
    }
}
/*전화번호 유효성 검사*/
function telWrite(ths){
    let tel_val1 = $('#tel1').val();
    let tel_val2 = $('#tel2').val();
    let tel_val3 = $('#tel3').val();
    let tel_whole = tel_val1 + '-' + tel_val2 + '-' + tel_val3;
    if(checkTel.test(tel_whole)){
        $('#telchk').hide();
        $("#telcheck").attr('value', "ok");
        $('#tel_whole').attr('value', tel_whole);
    }
    else{
        $('#telchk').show();
        $("#telcheck").attr('value', "no");
        $('#tel_whole').attr('value', '');
    }
}
/*확인 버튼 클릭시 */
function memWholeChk(){
    let idcheck = $('#idcheck').val();
    let pwcheck = $('#pwcheck').val();
    let namecheck = $('#namecheck').val();
    let addresscheck = $('#addresscheck').val();
    let telcheck = $('#telcheck').val();
    if(idcheck == 'ok'
    && pwcheck == 'ok'
    && namecheck == 'ok'
    && addresscheck == 'ok'
    && telcheck == 'ok'){
        $('#memberForm').submit();
    }else{
        alertShow("회원가입 오류", "모든 내용을 적어주세요.");
    }
}

//로그인
function loginWrite (object){
    let object_id = object.dataset.id;
    let object_type = object.dataset.type;
    let object_val = $('#'+object_id).val();
    if(object_val != ''){
        $('#'+object_type).addClass('keyon');
    }
    else{
        $('#'+object_type).removeClass('keyon');
    }
}
function idPwChk(){
    let id_val = $('#id').val();
    let pw_val = $('#pw').val();
    $.ajax({
        type: "post",
        url: "/idpwChk",
        async: true,
        data: {"id":id_val,"pw":pw_val},
        success:function(data){
            console.log(data);
            if(data){
                if(data=='true'){
                    alertShow("로그인 성공", "성공적으로 로그인 되었습니다.");
                    $('.common_flex').css('display','none');
                    setTimeout(function(){
                        $('#memberLogin').submit();
                        alertHide();
                    $('.common_flex').css('display','flex');
                    },1500);
                }else{
                    alertShow("로그인 실패","아이디 혹은 비밀번호가 다릅니다.");
                    return false;
                }
            }
        },
        error:function(data){
            alertShow("로그인 오류","다시 한 번 시도해주세요.");
            return false;
        }
    });
}