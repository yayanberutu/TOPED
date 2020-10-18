/**
 * 
 */

$('document').ready(function(){

	
	$('.table .btn').on('click', function(event){
		
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href, function(item, status){
			$('#idEdit').val(item.id);
			$('#item_nameEdit').val(item.item_name);
			$('#stockEdit').val(item.stock);
			$('#priceEdit').val(item.price);
			$('#soldEdit').val(item.sold);
			$('#seenEdit').val(item.seen);
			$('#weightEdit').val(item.itemDetail.weight);
			$('#conditionEdit').val(item.itemDetail.condition);
			$('#insuranceEdit').val(item.itemDetail.insurance);
			$('#categoryEdit').val(item.itemDetail.category);
		});
		
	
		$('#editModal').modal();
		
	});
	
	$('.table #buyButton').on('click', function(event){
		
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href, function(item, status){
			$('#idBuy').val(item.id);
			console.log(item.item_name);
		});
		
	
		$('#buyModal').modal();
		
	});
	
	
	$('.table #deleteButton').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #delRef').attr('href', href);
		$('#deleteModal').modal();
	})
	
});