
function pstFaceBook(msg) {
	var url		= location.href;
    var href	= "http://www.facebook.com/sharer.php?u="+encodeURIComponent(url)+"&t="+encodeURIComponent(msg);
    var pop 	= window.open(href, 'facebook', 'width=800, height=500');
    if(pop){
    	pop.focus();
    }
}


function pstTwitter(msg) {
	var url		= location.href;
	var href	= "http://twitter.com/home?status="+encodeURIComponent(msg)+" "+encodeURIComponent(url);
    var pop 	= window.open(href, 'twitter', 'width=800, height=500');
    if(pop){
    	pop.focus();
    }
}
