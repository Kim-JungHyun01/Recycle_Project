$(document).ready(function() {
    var selectedItem = null;

    // 품목 클릭 시 선택된 품목 표시
    $('.item-btn').on('click', function() {
        $('.item-btn').removeClass('bg-success text-white');
        $(this).addClass('bg-success text-white');
        selectedItem = $(this).attr('id');
        $('.select-btn').text($(this).text() + ' 선택됨 (품목 추가)');
    });

    // "추가할 품목을 선택하세요" 버튼 클릭 시 선택된 품목 추가
    $('.select-btn').on('click', function() {
        if (selectedItem) {
            var itemName = $('#' + selectedItem).text();
            var existingItem = $('#selectedItemsContainer option[value="' + selectedItem + '"]');
            var itemPrice = $('#' + selectedItem).data('price');

            if (existingItem.length > 0) {
                var countInput = existingItem.closest('.selected-item').find('.item-count');
                countInput.val(parseInt(countInput.val()) + 1);
                updatePrice(existingItem.closest('.selected-item'), itemPrice);
            } else {
                var selectBox = $('<select class="form-select"></select>');
                var countInput = $('<input type="number" class="form-control item-count" min="1" value="1">');
                var priceInput = $('<input type="text" class="form-control item-price" readonly placeholder="' + itemPrice + ' 원">');
                var deleteBtn = $('<button class="btn btn-danger btn-sm delete-btn">삭제</button>');

                selectBox.append('<option value="' + selectedItem + '" selected>' + itemName + '</option>');

                var itemContainer = $('<div class="selected-item"></div>');
                itemContainer.append(selectBox, countInput, priceInput, deleteBtn);

                $('#selectedItemsContainer').append(itemContainer);
                updatePrice(itemContainer, itemPrice);
            }

            $('.item-btn').removeClass('bg-success text-white');
            selectedItem = null;
            $('.select-btn').text('추가할 품목을 선택하세요');
        }
    });

    // 수량 입력 시 1 미만 방지
    $(document).on('input', '.item-count', function() {
        var count = $(this).val();
        // 유효성 검사: 1 이상이어야 하고, 숫자여야 한다.
        if (isNaN(count) || count < 1) {
            $(this).val(1);  // 잘못된 값이 입력되면 1로 되돌림
        }
        var itemPrice = $(this).closest('.selected-item').find('.item-price').data('price'); // 가격 가져오기
        updatePrice($(this).closest('.selected-item'), itemPrice); // 가격 업데이트
    });

    // 삭제 버튼 기능
    $(document).on('click', '.delete-btn', function() {
        $(this).closest('.selected-item').remove();
    });

    // 가격 업데이트 함수
    function updatePrice(itemContainer, itemPrice) {
        var count = parseInt(itemContainer.find('.item-count').val());  // 수량
        if (isNaN(count) || count < 1) {
            count = 1;  // 수량이 1 미만이면 1로 설정
        }
        var totalPrice = itemPrice * count;  // 총 가격 계산
        itemContainer.find('.item-price').val(totalPrice + ' 원');  // 가격 입력란에 업데이트
    }

});
