$(document).ready(function () {

    $('.bowlTableTd').on('click','.receiptBtn',function(){
		var $this	= $(this);
		var $bow	= $('.bowlTableTd');
		var len		= $bow.find('.formTable').length;
		var $clone	= {};
		if($this.hasClass('addbtn')){
			var $clone	= tableAddFn();
			$bow.append($clone);
		}else{
			tableDelFn();
		}
		function tableAddFn(){
			if(len >= 5){
				alert('※ 최대 5개까지 등록 가능합니다.');
			}
			else{
			
				$clone	= $bow.find('.formTable').eq(0).clone();
				$clone.find('input,a').each(function(){
					var asd = "";
			        if(this.type == 'text') this.value = ''; this.id = this.id+"_"+len; asd = this.id;
				});
				
				return $clone;
			}
		}
		function tableDelFn(){
			if(len > 1){
				$this.closest('.formTable').remove();
			}else{
				alert('※ 1개 이상일때 삭제 가능합니다.');
			}
		}
	});
    
    
    $('.bowlTableTd2').on('click','.receiptBtn',function(){
		var $this	= $(this);
		var $bow	= $('.bowlTableTd2');
		var len		= $bow.find('.formTable2').length;
		var $clone	= {};
		if($this.hasClass('addbtn')){
			var $clone	= tableAddFn();
			$bow.append($clone);
		}else{
			tableDelFn();
		}
		function tableAddFn(){
			if(len >= 5){
				alert('※ 최대 5개까지 등록 가능합니다.');
			}
			else{
				$clone	= $bow.find('.formTable2').eq(0).clone();
				$clone.find('input,a').each(function(){
					var asd = "";
			        if(this.type == 'text') this.value = ''; this.id = this.id+"_"+len; asd = this.id;
				});
				
				return $clone;
			}
		}
		function tableDelFn(){
			if(len > 1){
				$this.closest('.formTable2').remove();
			}else{
				alert('1개 이상일때 삭제 가능합니다.');
			}
		}
	});
    
    
    $('.bowlTableTd3').on('click','.receiptBtn',function(){
		var $this	= $(this);
		var $bow	= $('.bowlTableTd3');
		var len		= $bow.find('.formTable3').length;
		var $clone	= {};
		if($this.hasClass('addbtn')){
			var $clone	= tableAddFn();
			$bow.append($clone);
		}else{
			tableDelFn();
		}
		function tableAddFn(){
			if(len >= 5){
				alert('※ 최대 5개까지 등록 가능합니다.');
			}
			else{
				$clone	= $bow.find('.formTable3').eq(0).clone();
				$clone.find('input,a').each(function(){
					var asd = "";
			        if(this.type == 'text') this.value = ''; this.id = this.id+"_"+len; asd = this.id;
				});
				
				return $clone;
			}
		}
		function tableDelFn(){
			if(len > 1){
				$this.closest('.formTable3').remove();
			}else{
				alert('1개 이상일때 삭제 가능합니다.');
			}
		}
	});
    
    $('.bowlTableTd4').on('click','.receiptBtn',function(){
		var $this	= $(this);
		var $bow	= $('.bowlTableTd4');
		var len		= $bow.find('.formTable4').length;
		var $clone	= {};
		if($this.hasClass('addbtn')){
			var $clone	= tableAddFn();
			$bow.append($clone);
		}else{
			tableDelFn();
		}
		function tableAddFn(){
			$clone	= $bow.find('.formTable4').eq(0).clone();
			$clone.find('input,a').each(function(){
				var asd = "";
		        if(this.type == 'text') this.value = ''; this.id = this.id+"_"+len; asd = this.id;
			});
			
			return $clone;
		}
		function tableDelFn(){
			if(len > 1){
				$this.closest('.formTable4').remove();
			}else{
				alert('1개 이상일때 삭제 가능합니다.');
			}
		}
	});
    
    
});