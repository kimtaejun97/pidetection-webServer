var main = {
    init: function () {
        var _this = this;

        $('#btn-upload').on("click",function (){
            _this.upload()
        });
    },


    upload : function () {
        var flag = true

        var formData = new FormData($('#input-image')[0]);
        $.ajax({
            type: 'POST',
            url: '/api/detection/input',
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            dataType: 'text',

            success: function (res) {
                flag= false
                window.location.href=res;

            },
            error: function (e) {
                alert("파일 업로드 실패: " + JSON.stringify(e));
            }


        });

        alert("파일 업로드 완료.");
        var waitMsg =document.getElementById("wait-msg");
        waitMsg.innerText = "매칭중..."



    },



};

main.init();




