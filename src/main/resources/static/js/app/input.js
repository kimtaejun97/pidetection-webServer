var main = {
    init: function () {
        var _this = this;

        $('#btn-upload').on("click",function (){
            _this.inputViewResult()
            _this.upload()
        }),

        $('#input_file').change(function (){
            console.log("input change");
            _this.inputView(this);
        });
    },

    inputView : function (input){
        if(input.files && input.files[0]){
            var reader = new FileReader();

            reader.onload = function (e){
                console.log(e);
                $('.input-image-view').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }

    },

    inputViewResult : function (){
        var formData = new FormData($('#input-image')[0]);


        $.ajax({
            type: 'POST',
            url: '/api/result/inputView',
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            dataType: 'text',

            success: function (res) {
            },
            error: function (e) {
                alert("파일 업로드 실패: " + JSON.stringify(e));
            }
        });
    },

    upload : function () {
        var formData = new FormData($('#input-image')[0]);

        $.ajax({
            type: 'POST',
            url: 'http://218.239.8.6:8080/api/detection/input',
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            dataType: 'text',
            xhrFields : { withCredentials : true},

            success: function (res) {
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




