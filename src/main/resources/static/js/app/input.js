var main = {
    init: function () {
        var _this = this;

        $('#btn-upload').on("click",function (e){
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
        var fileSize = formData.get("image").size;

        //size check
        if(fileSize < 10485760){
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
        }

    },

    upload : function () {
        let formData = new FormData($('#input-image')[0]);
        console.log(formData.get("image").size)
        var fileSize = formData.get("image").size;
        var imgName = formData.get("image").name.split(".")
        var ext = imgName[imgName.length-1]
        ext = ext.toLowerCase();

        //file type check
        if(! ["jpg","jpeg","png"].includes(ext)){
            alert("jpg,jpeg,png 파일을 넣어주세요.")
        }
        //size check
        else if(fileSize > 10485760){
            alert("이미지 사이즈가 너무 큽니다.(제한 크기: 10MB)")
            return ;
        }
        else{
                $.ajax({
                    type:'POST',
                    url: '/api/sessionId',
                    dataType : 'text',
                    async: false,
                    success: function (sessionId){
                        formData.append("sessionId",sessionId);
                    },
                    fail : function (e) {

                    }
                });


            $.ajax({
                type: 'POST',
                url: 'http://218.239.8.6:8080/api/detection/input',
                // url: '/api/detection/input',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                dataType: 'text',

                success: function (res) {
                    if(res=="얼굴 미검출"){
                        alert("이미지에서 얼굴을 찾을 수 없습니다. 다른 이미지로 다시 시도하세요.");
                        afterUpload("", false,false);

                    }
                    else{
                        window.location.href=res;
                    }

                },
                error: function (e) {
                    alert("이미지 업로드 실패: " + JSON.stringify(e));
                    afterUpload("", false,false);

                }
            });
            afterUpload("매칭중...",true,true);
            alert("이미지 업로드 완료.");

        }
        function afterUpload (text, btn_upload, btn_input){
            document.getElementById("wait-msg").innerText =text;
            document.getElementById("btn-upload").disabled =btn_upload;
            document.getElementById("input_file").disabled =btn_input;
        }

    },


};

main.init();




