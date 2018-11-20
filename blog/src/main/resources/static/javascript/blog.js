Date.prototype.format = function (formatStr) {
    var str = formatStr;
    var Week = ['日', '一', '二', '三', '四', '五', '六'];
    str = str.replace(/yyyy|YYYY/, this.getFullYear());
    str = str.replace(/MM/, (this.getMonth() + 1) > 9 ? (this.getMonth() + 1).toString() : '0' + (this.getMonth() + 1));
    str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
    return str;
}

var pageNum = parseInt($('#pageNum').val());


function loadMore() {
    $('#load-more').attr('disabled', 'disabled').text('加载中...');
    $('#loading').show();
    var url = "/category/" + currentCategory + "/" + pageNum;
    if (typeof ($('#author').val()) != 'undefined') {
        url += '?author=' + $('#author').val();
    }
    $.post(url, function (result) {
        pageNum += 1;
        var html = "";
        try {
            for (var i = 0; i < result.content.length; i++) {
                var news = result.content[i];
                if (i == 0) {
                    html += '<div class="row news-list" id="pageNum' + pageNum + '">'
                } else {
                    html += '<div class="row news-list">';
                }
                var url = news.url;
                if(news.content!=null){
                    url = "/article/"+news.id;
                }
                html += '<div class="col-md-12"><div class="article"><div class="title"><a target="_blank"' +
                    ' href="'
                    + url +
                    '">' + news.title + '</a>&nbsp;&nbsp;<span  class="title-author"> ' + news.author
                    + '</span></div><div class="summary"><p >' + news.summ
                    + '</p><p class="publish-date">' + new Date(news.publishDate).format('yyyy-MM-dd')
                    + '</p><hr></div></div></div></div>';

            }
        } catch (err) {
            console.log(err);
        }
        $("#news-list-all").append(html);
        $('#load-more').text('加载更多').removeAttr('disabled');
    });

}