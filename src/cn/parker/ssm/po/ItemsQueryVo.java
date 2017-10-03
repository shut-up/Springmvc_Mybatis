package cn.parker.ssm.po;

public class ItemsQueryVo {
	private Items items;
	private ItemsCustom itemsCustom;
	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}
	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}
	public Items getItems() {
		return items;
	}
	public void setItems(Items items) {
		this.items = items;
	}
}
