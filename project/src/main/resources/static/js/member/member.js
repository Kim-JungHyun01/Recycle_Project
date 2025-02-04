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
                if(data == null || data == "null" || data == ""){
                    alertShow('중복확인',"사용가능한 아이디 입니다.");
                    $('#idcheck').val("yes");
                }
                else{
                    alertShow('중복확인',"이미 사용중인 아이디 입니다.");
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
}
/*비밀번호 타입 변경*/
function pwTxtPw(ths){
    var pw_id = ths.dataset.id;
    var pw_type = $('#'+pw_id).attr('type');
    if(pw_type == 'password'){
        $('#'+pw_id).attr('type', 'text');
    }
    else{
        $('#'+pw_id).attr('type', 'password');
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
        $("#namechk").attr('value', "no");
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
    }
    else{
        $('#telchk').show();
        $("#telcheck").attr('value', "no");
    }
}
/*확인 버튼 클릭시 */
function memWholeChk(){
    let idcheck = $('#idcheck').val();
    let pwcheck = $('#pwcheck').val();
    let namecheck = $('#namecheck').val();
    let addresscheck = $('#addresscheck').val();
    let telcheck = $('#telcheck').val();
}