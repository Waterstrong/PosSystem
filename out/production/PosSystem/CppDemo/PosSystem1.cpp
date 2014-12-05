// PosSystem.cpp : 定义控制台应用程序的入口点。
//
/*
商店里进行购物结算时会使用收银机（POS）系统，这台收银机会在结算时根据客户的购物车（Cart）中的商品（Item）和
商店正在进行的优惠活动（Promotion）进行结算和打印购物清单。

已知该商店正在对部分商品进行“指定商品打折”、“第二件商品半价”等优惠活动，如遇到某件商品存在多种优惠活动的
情况时，所有优惠活动同时适用。例如商品A原价100，现在打8折，且第二件半价，那么购买两件的总价为120；

现在已知
1st：有商品文件列表  itemlist.txt，内容如下：

ITEM000001:40
ITEM000003:50
ITEM000005:60

ITEM000001代表商品的条形码，对于商品来说条形码是唯一的，40代表该商品的单价；

2nd：“第二件商品半价”优惠列表 second_half_price_promotion.txt 内容如下：

ITEM000001
ITEM000003

所有位于该优惠列表中的item，都适用于第二件半价；而优惠列表不包含的item则不参加该优惠活动；

3rd：“指定商品打折”优惠活动文本文件 discount_promotion.txt 内容如下：

ITEM000001:75
ITEM000005:90

所有位于该优惠列表中的商品都按照指定的折扣出售，如ITEM000001为7.5折；而优惠列表不包含的item则无折扣；

4th：购物车选购商品列表cart.txt，内容如下：

ITEM000001
ITEM000005
ITEM000001-3
ITEM000003-2
ITEM000005-2
ITEM000001
ITEM000005

该购物车中ITEM000001-3代表商品ITEM000001的数量是3件，"-"之后表示数量，如果没有"-"则默认数量是一；在该购物车中选购的商品数量分别为：

5件ITEM000001
2件ITEM000003
4件ITEM000005

在已知以上所有数据的情况下，需要实现一个购物车结算系统，通过购物车中的所购商品进行结算和打印购物清单，打印包含如下内容：

购物明细（数量  单价  小计）
总计金额（优惠前  优惠后  优惠差价）

上述的示例数据得到的打印输出应该类似于：

购物明细（数量 单价 小计）
item1            5   40    120
item3            2   50    75
item5            4   60    216

总计金额（优惠前  优惠后  优惠差价）
411         540    411     129
*/
#include "stdafx.h"

#include <iostream>
#include <fstream>
#include <sstream>
#include <hash_map>
#include <hash_set>
#include <string>
using namespace std;

#define ErrorFile(err) cout<<"File <"+err+"> don't exist."<<endl
#define ErrorDirect(err) cout<<err<<endl

const string FileFold = "item_db";

//************************************
// @Method   : Str2Num
// @FullName : <T>::Str2Num
// @Access   : public 
// @Qualifier:
// @Parameter: const string & str
// @Returns  : T
// @Brief    : convert string to special Type
//************************************
template<typename T>  
T Str2Num(const string& str)
{  
	istringstream iss(str);
	T num;
	iss>>num;
	return num;
}

// file stream operation interface
class IFileStream
{
protected:
	virtual void HandleItem(string) = 0;
public:
	void LoadItemFromFile(string fileName)
	{
		ifstream in(FileFold+"\\"+fileName, ios::in);
		if (in == NULL)
		{
			ErrorFile(fileName);
			return;
		}
		string item;
		while (!in.eof())
		{
			if(in.fail())break;
			in>>item;
			HandleItem(item); // handle item string
		}
		in.close();
	}
};

// product and item list, contains barcode and price; itemlist.txt
class ItemList : public IFileStream
{
private:
	hash_map<string, float> m_items; // items contains barcode and price
protected:
	//************************************
	// @Method   : HandleItem
	// @FullName : ItemList::HandleItem
	// @Access   : public 
	// @Qualifier:
	// @Parameter: string item
	// @Returns  : void
	// @Brief    : load items from text file and handle them 
	//************************************
	void HandleItem(string item)
	{
		size_t pos = item.find(":");
		if (pos != string::npos)
		{ // get the barcode and price from string in file
			InsertItem(item.substr(0, pos), Str2Num<float>(item.substr(pos+1)));
		}
		else
		{
			ErrorDirect("Item parsed error.");
		}
	}
public:
	ItemList()
	{
		LoadItemFromFile("itemlist.txt");
	}
	void ClearItem()
	{
		m_items.clear();
	}
	//************************************
	// @Method   : InsertItem
	// @FullName : ItemList::InsertItem
	// @Access   : public 
	// @Qualifier:
	// @Parameter: string barcode
	// @Parameter: float price
	// @Returns  : void
	// @Brief    : insert item into hash-map, construct items. 
	//************************************
	void InsertItem(string barcode, float price)
	{
		m_items.insert(make_pair(barcode, price));
	}
	//************************************
	// @Method   : GetItemPrice
	// @FullName : ItemList::GetItemPrice
	// @Access   : public 
	// @Qualifier:
	// @Parameter: string barcode
	// @Returns  : float
	// @Brief    : get one item's price
	//************************************
	float GetItemPrice(string barcode)
	{
		return m_items[barcode];
	}
};

class Item
{
protected:
	string m_barcode;
	float m_price;
	int m_amount;
public:
	Item(string barcode, float price, int amount)
	{
		m_barcode = barcode;
		m_price = price;
		m_amount = amount;
	}
	string GetBarcode() const
	{
		return m_barcode;
	}
	float GetPrice() const
	{
		return m_price;
	}
	void SetPrice(float price)
	{
		m_price = price;
	}
	int GetAmount() const
	{
		return m_amount;
	}
	void SetAmount(int amount)
	{
		m_amount = amount;
	}
	float GetSubtotal() const
	{
		return m_price * m_amount;
	}
};


// promotion activity, super class
class Promotion
{
protected:
	Promotion *m_nextPromotion;
public:
	Promotion()
	{
		m_nextPromotion = NULL;
	}
	void SetNextPromotion(Promotion *next)
	{
		m_nextPromotion = next;
	}

	// accept the promotion in the chain and return the subtotal
	float AcceptPromotion(Item item)
	{
		HandlePromotion(item);
		 // handle specific promotion
		if (m_nextPromotion != NULL)
		{
			return m_nextPromotion->AcceptPromotion(item); // go to next promotion
		}
		else
		{
			return item.GetSubtotal();
		}
	}
protected:
	//************************************
	// Method   : HandlePromotion
	// FullName : Promotion::HandlePromotion
	// Access   : virtual protected 
	// Qualifier:
	// Parameter: Item &
	// Returns  : void
	// Brief    : handle specific promotion and return the promotion of whole
	//************************************
	virtual void HandlePromotion(Item&) = 0;
};

// second half price promotion
class SecondHalfPrice : public Promotion, public IFileStream
{
protected:
	int m_discountStandard;
	float m_discountDegree;
	hash_set<string> m_promotionList;
	void HandleItem(string item)
	{
		m_promotionList.insert(item);
	}
public:
	SecondHalfPrice():Promotion()
	{
		LoadItemFromFile("second_half_price_promotion.txt");
		m_discountStandard = 2;
		m_discountDegree = 0.5;
	}
	void ClearPromotionList()
	{
		m_promotionList.clear();
	}
	void InsertPromotionList(string barcode)
	{
		m_promotionList.insert(barcode);
	}
	void HandlePromotion(Item& item)
	{
		if (m_promotionList.find(item.GetBarcode()) != m_promotionList.end())
		{
			int discountAmount = item.GetAmount() / m_discountStandard;
			float totalPrice = item.GetPrice() * m_discountDegree * discountAmount + (item.GetAmount()-discountAmount)*item.GetPrice();
			item.SetPrice(totalPrice / item.GetAmount());
		}
	}
};

// discount promotion
class Discount : public Promotion, public IFileStream
{
protected:
	hash_map<string, float> m_promotionList;
	void HandleItem(string item)
	{
		size_t pos = item.find(":");
		if (pos != string::npos)
		{ // get the barcode and discount from string in file
			m_promotionList.insert(make_pair( item.substr(0, pos), Str2Num<float>(item.substr(pos+1))/100 ));
		}
		else
		{
			ErrorDirect("Discount item parsed error.");
		}
	}
public:
	Discount():Promotion()
	{
		LoadItemFromFile("discount_promotion.txt");
	}
	void ClearPromotionList()
	{
		m_promotionList.clear();
	}
	void InsertPromotionList(string barcode, float discount)
	{
		m_promotionList.insert(make_pair( barcode, discount/100 ));
	}
	void HandlePromotion(Item& item)
	{
		if (m_promotionList.find(item.GetBarcode()) != m_promotionList.end())
		{
			item.SetPrice(item.GetPrice() * m_promotionList[item.GetBarcode()]);
		}
	}
};


// shopping cart, store items that are already selected. cart.txt
class ShoppingCart : public IFileStream
{
private:
	hash_map<string, int> m_cart;// items in cart contains barcode and its amount
protected:
	void HandleItem(string item) // hand items in cart
	{
		size_t pos = item.find("-");
		if (pos != string::npos)
		{
			// add the barcode and amount from string in file
			m_cart[item.substr(0, pos)] += Str2Num<int>(item.substr(pos+1));
		}
		else
		{ // default as one item
			m_cart[item.substr(0, pos)] += 1;
		}
	}
public:
	ShoppingCart()
	{
		LoadItemFromFile("cart.txt");		
	}
	void ClearCart()
	{
		m_cart.clear();
	}
	//************************************
	// @Method   : InsertCart
	// @FullName : ShoppingCart::InsertCart
	// @Access   : public 
	// @Qualifier:
	// @Parameter: string barcode : barcode is the primary key
	// @Parameter: int amount  : how many items in the cart
	// @Returns  : void
	// @Brief    : insert into cart with barcode and its amount
	//************************************
	void InsertCart(string barcode, int amount)
	{
		m_cart.insert(make_pair(barcode, amount));
	}
	int GetItemAmount(string barcode)
	{
		return m_cart[barcode];
	}
	void ApplyPrintSettlement(ItemList *itemList, Promotion *promotion, bool multiPromotion = true)
	{
		cout<<"购物明细    （数量	单价	小计）"<<endl;
		float beforePromotion = 0;
		float afterPromotion = 0;
		for (hash_map<string, int>::iterator it = m_cart.begin(); it != m_cart.end(); ++it)
		{
			float price = itemList->GetItemPrice(it->first);
			beforePromotion += it->second * price;
			Item item(it->first, price, it->second);
			float subtotal = promotion->AcceptPromotion(item);
			 afterPromotion += subtotal;
			cout<<it->first<<"	"<<it->second<<"	"<<price<<"	"<<subtotal<<endl;
		}
		cout<<"总计金额（优惠前  优惠后  优惠差价）"<<endl;
		cout<<"   "<<afterPromotion<<"	    "<<beforePromotion<<"	   "<<afterPromotion<<"	   "<<beforePromotion-afterPromotion<<endl;
	}
};

int _tmain(int argc, _TCHAR* argv[])
{

	ItemList itemList;

	SecondHalfPrice secondHalfPrice;
	Discount discount;
	secondHalfPrice.SetNextPromotion(&discount);
	//discount.SetNextPromotion(&secondHalfPrice);

	ShoppingCart shoppingCart;
	shoppingCart.ApplyPrintSettlement(&itemList, &secondHalfPrice);

	system("pause");
	return 0;
}

