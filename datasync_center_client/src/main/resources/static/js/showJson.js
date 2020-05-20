$(function(){
　　$('#select').bind('click',showJson);
    //$('#download').bind('click', download);
    //$('#file1').change(function() {
    //$('#fileName1').val($(this).val());
   // });

    //$('#file2').change(function() {
     //   $('#fileName2').val($(this).val());
    //});

    $('#copy').bind('click',copyJson);
    $('#copy-warn').bind('click',copyWarn);
    $('#showWarn').bind('click',showWarn);
    $('#datetimepicker1').datetimepicker({
        format: 'YYYY-MM-DD',
        locale: moment.locale('zh-cn'),
        date:new Date()
    });
});

 warning = "no warning";

function showWarn(){
    var formData = new FormData();
    var infoDate = document.getElementById('date').value;
    document.getElementById('warn-panel').style.display = 'block';
    formData.append('date', infoDate);
    $.ajax({
        type: 'POST',
        url: '/getLogError',
        data: formData ,
        contentType: false,
        processData: false,
        success: function (data) {
            //console.log(data);
            if(data.success){
                document.getElementById('warn-info').innerText= data.data;
            }else{
                document.getElementById('warn-info').innerText="";
                $('.alert1').attr('class','alert1 alert1-danger').html(data.message).show().delay(5000).fadeOut();
            }

        },
        error:function (data) {
            warning = "no warning";
            if(data.responseJSON.error.message == null){
                $('.alert1').attr('class','alert1 alert1-danger').html("unknown error").show().delay(5000).fadeOut();
            }else{
                $('.alert1').attr('class','alert1 alert1-danger').html(data.responseJSON.error.message).show().delay(5000).fadeOut();
            }
            document.getElementById('out_pre').innerText='';
        }
    })
}

function copyWarn(){
     try{
         range = document.createRange();
         range.selectNode(document.getElementById('warn-info'));
         selection = window.getSelection();
         if(selection.rangeCount>0){
             selection.removeAllRanges();
         }
         selection.addRange(range);
         document.execCommand('copy');
         selection.removeRange(range);
         $('.alert1').attr('class','alert1 alert1-success').html('copy success').show().delay(1500).fadeOut();
        }
     catch(err){
           $('.alert1').attr('class','alert1 alert1-danger').html('copy failed').show().delay(4500).fadeOut();
            //$('<div>').appendTo('body').addClass('alert alert-success').html('copy failed').show().delay(1500).fadeOut();
        }
}

function initialize(){
    var a=screen.availWidth;
    var b=screen.availHeight;
    window.moveTo(0,0)
    window.resizeTo(a,b)

     $.ajaxSetup({
           layerIndex:-1,
           beforeSend: function(jqXHR, settings) {
               this.layerIndex = layer.load(1);
           },
           complete: function () {
           layer.close(this.layerIndex);
           }
     });
}

function copyJson(){
    try{
         range = document.createRange();
         range.selectNode(document.getElementById('out_pre'));
         selection = window.getSelection();
         if(selection.rangeCount>0){
            selection.removeAllRanges();
         }
         selection.addRange(range);
         document.execCommand('copy');
         selection.removeRange(range);
         $('.alert1').attr('class','alert1 alert1-success').html('copy success').show().delay(1500).fadeOut();

    }
     catch(err){
        $('.alert1').attr('class','alert1 alert1-danger').html('copy failed').show().delay(4500).fadeOut();
        //$('<div>').appendTo('body').addClass('alert alert-success').html('copy failed').show().delay(1500).fadeOut();
    }
}

var  downloadButton = document.getElementById("download")
function showJson(){
    document.getElementById('warn-panel').style.display = 'none';
    //document.getElementById('warn-info').innerText= "";
    var formData = new FormData();
    var infoDate = document.getElementById('date').value;

    formData.append('date', infoDate);
    $.ajax({
         type: 'POST',
         url: '/getLogInfo',
         data: formData ,
         contentType: false,
         processData: false,
         success: function (data) {
             //console.log(data);
             if(data.success){
                 document.getElementById('out_pre').innerText= data.data;
             }else{
                 document.getElementById('out_pre').innerText= "";
                 $('.alert1').attr('class','alert1 alert1-danger').html(data.message).show().delay(5000).fadeOut();
             }

         },
         error:function (data) {

               warning = "no warning";
               if(data.responseJSON.error.message == null){
                    $('.alert1').attr('class','alert1 alert1-danger').html("unknown error").show().delay(5000).fadeOut();
               }else{
                    $('.alert1').attr('class','alert1 alert1-danger').html(data.responseJSON.error.message).show().delay(5000).fadeOut();
               }
               document.getElementById('out_pre').innerText='';
         }
    })

}







