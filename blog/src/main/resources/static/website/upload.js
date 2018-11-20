$("#file").fileinput({
    language:lang,
    allowedFileExtensions: ['pdf'],//接收的文件后缀
    // showUpload: true, //是否显示上传按钮
    // showCaption: false,//是否显示标题
    minImageWidth: 50, //图片的最小宽度
    minImageHeight: 50,//图片的最小高度
    maxImageWidth: 1000,//图片的最大宽度
    maxImageHeight: 1000,//图片的最大高度
    theme: "explorer",
    uploadUrl: "/upload",
    minFileCount: 1,
    maxFileCount: 20,
    overwriteInitial: false,
    // previewFileIcon: '<i class="fa fa-file"></i>',
    initialPreviewAsData: false, // defaults markup
    uploadExtraData: {
        img_key: "1000",
        img_keywords: "happy, nature"
    },
    preferIconicPreview: true, // this will force thumbnails to display icons for following file extensions
    previewFileIconSettings: { // configure your icon file extensions

        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',

    }

});

var i= 0;
//导入文件上传完成之后的事件
$("#file").on("fileuploaded", function (event, data, previewId, index) {
    if(i==0){
        $('#table').show();
    }
    var data = data.response;
    console.log(data);

    if (data.status==true) {
        console.log('上传成功');
        i++
        var tr = '<tr><td><input name="order" type="number" value="'+i+'" uuid="'+data.fileName+'"/></td>' +
            '<td>'+data.originFileName+'</td>' +
            '<td>'+data.size+'</td>' +
            '<td>'+data.fileName+'</td></tr>'
        $('#table-body').append(tr);
    }



});

$('.btn-info').click(function(){
    var param = "";
    $('input[name="order"]').each(function(i,dom){
        var input = $(this);
        var order = input.val();
        var uuid = input.attr('uuid');
        param+=order+","+uuid+"enilu";
    });
    window.location.href="/merge?param="+param;
})