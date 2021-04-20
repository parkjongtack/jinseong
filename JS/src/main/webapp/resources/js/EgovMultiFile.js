function MultiSelector( list_target, max ){

	// Where to write the list
	this.list_target = list_target;
	// How many elements?
	this.count = 0;
	// How many elements?
	this.id = 0;
	// Is there a maximum?
	if( max ){
		this.max = max;
	} else {
		this.max = -1;
	};
	
	// 첨부불가
	this.invalidExt = ['asp', 'aspx', 'php', 'jsp', 'html', 'htm', 'cgi', 'pl', 'js', 'exe', 'bat'];
	
	// 이미지 타입일경우 첨부가능
	this.invalidImg = ['jpge', 'jpg', 'gif', 'png', 'tif'];
	
	// get extention
	this.getExt = function(path) {
		return path.substring(path.lastIndexOf('.') + 1).toLowerCase();
	};
	
	/**
	 * Add a new file input element
	 */
	this.addElement = function( element ){
		var that = this;
		
		// Make sure it's a file input element
		if( element.tagName == 'INPUT' && element.type == 'file' ){

			// Element name -- what number am I?
			element.name = 'file_' + this.id++;

			// Add reference to this object
			element.multi_selector = this;

			// What to do when a file is selected
			element.onchange = function(){
								
				var ext = that.getExt(this.value);
				
				var valid = true;
				that.invalidExt.forEach(function(invalid) {
					if (ext === invalid)
						valid = false;
				});
				
				if (!!! valid) {
					this.value = '';
					alert('첨부가 불가능한 파일 형식 입니다.');
					return;
				}

				// New file input
				var new_element = document.createElement( 'input' );
				new_element.type = 'file';
				new_element.title = '첨부파일입력';
				// Add new element
				this.parentNode.insertBefore( new_element, this );

				// Apply 'update' to element
				this.multi_selector.addElement( new_element );

				// Update list
				this.multi_selector.addListRow( this );
				
				// Hide this: we can't use display:none because Safari doesn't like it
				this.style.position = 'absolute';
				this.style.left = '-1000px';
				this.style.top = '-1000px';
				this.style.display = 'none';
				this.style.visibility = 'hidden';
				this.style.width = '0';
				this.style.height = '0';
				this.style.overflow = 'hidden';
				this.setAttribute('class','uploadAtchFile');

				new_element.onkeypress = function(){
					return false;
				};

			};
			// If we've reached maximum number, disable input element
			if( this.max != -1 && this.count >= this.max ){
				element.disabled = true;
			};

			// File element counter
			this.count++;
			// Most recent element
			this.current_element = element;
			
		} else {
			// This can only be applied to file input elements!
			alert( 'Error: not a file input element' );
		};

	};
	
	
	this.addElement2 = function( element ){
		var that = this;
		
		// Make sure it's a file input element
		if( element.tagName == 'INPUT' && element.type == 'file' ){

			// Element name -- what number am I?
			element.name = 'img_' + this.id++;

			// Add reference to this object
			element.multi_selector = this;

			// What to do when a file is selected
			element.onchange = function(){
								
				var ext = that.getExt(this.value);
				
				var valid = false;
				that.invalidImg.forEach(function(invalid) {
					if (ext === invalid)
						valid = true;
				});
				
				if (!!! valid) {
					this.value = '';
					alert('첨부가 불가능한 파일 형식 입니다.');
					return;
				}

				// New file input
				var new_element = document.createElement( 'input' );
				new_element.type = 'file';
				new_element.title = '첨부파일입력';
				
				// Add new element
				this.parentNode.insertBefore( new_element, this );

				// Apply 'update' to element
				this.multi_selector.addElement2( new_element );

				// Update list
				this.multi_selector.addListRow( this );
				
				// Hide this: we can't use display:none because Safari doesn't like it
				this.style.position = 'absolute';
				this.style.left = '-1000px';
				this.style.top = '-1000px';
				this.style.display = 'none';
				this.style.visibility = 'hidden';
				this.style.width = '0';
				this.style.height = '0';
				this.style.overflow = 'hidden';
				this.setAttribute('class','uploadImgFile');

				new_element.onkeypress = function(){
					return false;
				};

			};
			// If we've reached maximum number, disable input element
			if( this.max != -1 && this.count >= this.max ){
				element.disabled = true;
			};

			// File element counter
			this.count++;
			// Most recent element
			this.current_element = element;
			
		} else {
			// This can only be applied to file input elements!
			alert( 'Error: not a file input element' );
		};

	};
	this.addElement3 = function( element ){
		var that = this;
		
		// Make sure it's a file input element
		if( element.tagName == 'INPUT' && element.type == 'file' ){

			// Element name -- what number am I?
			element.name = 'file_' + this.id++;

			// Add reference to this object
			element.multi_selector = this;

			// What to do when a file is selected
			element.onchange = function(){
								
				var ext = that.getExt(this.value);
				
				var valid = true;
				that.invalidExt.forEach(function(invalid) {
					if (ext === invalid)
						valid = false;
				});
				
				if (!!! valid) {
					this.value = '';
					alert('첨부가 불가능한 파일 형식 입니다.');
					return;
				}

				// New file input
				var new_element = document.createElement( 'input' );
				new_element.type = 'file';
				new_element.title = '첨부파일입력';
				// Add new element
				this.parentNode.insertBefore( new_element, this );

				// Apply 'update' to element
				this.multi_selector.addElement3( new_element );

				// Update list
				this.multi_selector.addListRow2( this );
				
				// Hide this: we can't use display:none because Safari doesn't like it
				this.style.position = 'absolute';
				this.style.left = '-1000px';
				this.style.top = '-1000px';
				this.style.display = 'none';
				this.style.visibility = 'hidden';
				this.style.width = '0';
				this.style.height = '0';
				this.style.overflow = 'hidden';

				new_element.onkeypress = function(){
					return false;
				};

			};
			// If we've reached maximum number, disable input element
			if( this.max != -1 && this.count >= this.max ){
				element.disabled = true;
			};

			// File element counter
			this.count++;
			// Most recent element
			this.current_element = element;
			
		} else {
			// This can only be applied to file input elements!
			alert( 'Error: not a file input element' );
		};
		
	};

	/**
	 * Add a new row to the list of files
	 */
	this.addListRow = function( element ){

		// Row div
		var new_row = document.createElement( 'div' );

		// Delete button
		var new_row_button = document.createElement( 'input' );
		new_row_button.type = 'button';
		new_row_button.title = '첨부파일 삭제 버튼';
		new_row_button.value = 'Delete';
		new_row_button.className = 'file_cla';

		// References
		new_row.element = element;

		// Delete function
		new_row_button.onclick= function(){

			// Remove element from form
			this.parentNode.element.parentNode.removeChild( this.parentNode.element );

			// Remove this row from the list
			this.parentNode.parentNode.removeChild( this.parentNode );

			// Decrement counter
			this.parentNode.element.multi_selector.count--;

			// Re-enable input element (if it's disabled)
			this.parentNode.element.multi_selector.current_element.disabled = false;

			//    which nixes your already queued uploads
			return false;
		};

		// Set row value
		new_row.innerHTML = element.value;

		// Add button
		new_row.appendChild( new_row_button );

		// Add it to the list
		this.list_target.appendChild( new_row );
	};
	/**
	 * Add a new row to the list of files
	 * 코멘트 추가
	 */
	this.addListRow2 = function( element ){
		
		// Row div
		var new_row = document.createElement( 'div' );
		
		// Delete button
		var new_row_button = document.createElement( 'input' );
		new_row_button.type = 'button';
		new_row_button.title = '첨부파일 삭제 버튼';
		new_row_button.value = 'Delete';
		new_row_button.className = 'file_cla';
		
		
		// Comment
		var new_comment = document.createElement('input');
		new_comment.type = 'input';
		new_comment.title = '첨부파일 코멘트';
		new_comment.name = 'atch_file_comment';
		new_comment.setAttribute('class','w20p');
		new_comment.setAttribute('placeholder','내용을 입력하세요');
		new_comment.style.height = '18px';
		
		
		//placeholder="내용을 입력하세요"
		
		var new_label = document.createElement('label');
		new_label.style.padding = '0 10px';
		new_label.appendChild(document.createTextNode("코멘트를 입력하세요"));
		
		// References
		new_row.element = element;
		
		// Delete function
		new_row_button.onclick= function(){
			
			// Remove element from form
			this.parentNode.element.parentNode.removeChild( this.parentNode.element );
			
			// Remove this row from the list
			this.parentNode.parentNode.removeChild( this.parentNode );
			
			// Decrement counter
			this.parentNode.element.multi_selector.count--;
			
			// Re-enable input element (if it's disabled)
			this.parentNode.element.multi_selector.current_element.disabled = false;
			
			//    which nixes your already queued uploads
			return false;
		};
		
		// Set row value
		new_row.innerHTML = element.value;
		
		// Add button
		new_row.appendChild( new_row_button );
		//new_row.appendChild( new_comment );
		new_row.prepend( new_comment );
		//new_row.prepend( new_label );
		
		// Add it to the list
		this.list_target.appendChild( new_row );
	};

};