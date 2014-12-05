// PosSystem2.cpp : �������̨Ӧ�ó������ڵ㡣
//
/*
�̵�����й������ʱ��ʹ����������POS��ϵͳ����̨���������ڽ���ʱ���ݿͻ��Ĺ��ﳵ��Cart���е���Ʒ��Item����
�̵����ڽ��е��Żݻ��Promotion�����н���ʹ�ӡ�����嵥��

��֪���̵����ڶԲ�����Ʒ���С�ָ����Ʒ���ۡ������ڶ�����Ʒ��ۡ����Żݻ��������ĳ����Ʒ���ڶ����Żݻ��
���ʱ�������Żݻͬʱ���á�������ƷAԭ��100�����ڴ�8�ۣ��ҵڶ�����ۣ���ô�����������ܼ�Ϊ120��

������֪
1st������Ʒ�ļ��б�  itemlist.txt���������£�

ITEM000001:40
ITEM000003:50
ITEM000005:60

ITEM000001������Ʒ�������룬������Ʒ��˵��������Ψһ�ģ�40�������Ʒ�ĵ��ۣ�

2nd�����ڶ�����Ʒ��ۡ��Ż��б� second_half_price_promotion.txt �������£�

ITEM000001
ITEM000003

����λ�ڸ��Ż��б��е�item���������ڵڶ�����ۣ����Ż��б�������item�򲻲μӸ��Żݻ��

3rd����ָ����Ʒ���ۡ��Żݻ�ı��ļ� discount_promotion.txt �������£�

ITEM000001:75
ITEM000005:90

����λ�ڸ��Ż��б��е���Ʒ������ָ�����ۿ۳��ۣ���ITEM000001Ϊ7.5�ۣ����Ż��б�������item�����ۿۣ�

4th�����ﳵѡ����Ʒ�б�cart.txt���������£�

ITEM000001
ITEM000005
ITEM000001-3
ITEM000003-2
ITEM000005-2
ITEM000001
ITEM000005

�ù��ﳵ��ITEM000001-3������ƷITEM000001��������3����"-"֮���ʾ���������û��"-"��Ĭ��������һ���ڸù��ﳵ��ѡ������Ʒ�����ֱ�Ϊ��

5��ITEM000001
2��ITEM000003
4��ITEM000005

����֪�����������ݵ�����£���Ҫʵ��һ�����ﳵ����ϵͳ��ͨ�����ﳵ�е�������Ʒ���н���ʹ�ӡ�����嵥����ӡ�����������ݣ�

������ϸ������  ����  С�ƣ�
�ܼƽ��Ż�ǰ  �Żݺ�  �Żݲ�ۣ�

������ʾ�����ݵõ��Ĵ�ӡ���Ӧ�������ڣ�

������ϸ������ ���� С�ƣ�
item1            5   40    120
item3            2   50    75
item5            4   60    216

�ܼƽ��Ż�ǰ  �Żݺ�  �Żݲ�ۣ�
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

// item promotion
class Item
{
public:
	//************************************
	// @Method   : HandlePromotion
	// @FullName : Item::HandlePromotion
	// @Access   : virtual public 
	// @Qualifier:
	// @Parameter: string barcode
	// @Parameter: float rawPrice
	// @Parameter: int rawAmount
	// @Parameter: float newPrice
	// @Parameter: int newAmount
	// @Returns  : float
	// @Brief    : hand the promtion of item
	//************************************
	virtual float HandlePromotion(string barcode, float rawPrice, int rawAmount, float newPrice, int newAmount)
	{
		float subtotal = newPrice * newAmount;
		cout<<barcode<<"	"<<rawAmount<<"	"<<rawPrice<<"	"<<subtotal<<endl;
		return newPrice * newAmount;
	}
};

// promotion activity, decorator
class Promotion : public Item, public IFileStream
{
protected:
	Item *m_promotion;
public:
	Promotion() 
	{
		m_promotion = NULL;
	}
	void SetPromotion(Item *promotion)
	{
		m_promotion = promotion;
	}

	virtual float HandlePromotion(string barcode, float rawPrice, int rawAmount, float newPrice, int newAmount)
	{
		if (m_promotion != NULL)
		{
			return m_promotion->HandlePromotion(barcode, rawPrice, rawAmount, newPrice, newAmount);
		}
		return 0;
	}
};

// second half price promotion
class SecondHalfPrice : public Promotion
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
	float HandlePromotion(string barcode, float rawPrice, int rawAmount, float newPrice, int newAmount)
	{
		if (m_promotionList.find(barcode) != m_promotionList.end())
		{
			int discountAmount = newAmount / m_discountStandard;
			float totalPrice = newPrice * m_discountDegree * discountAmount + (newAmount-discountAmount)*newPrice;
			newPrice = totalPrice / newAmount;
		}
		return Promotion::HandlePromotion(barcode, rawPrice, rawAmount, newPrice, newAmount);
	}
};

// discount promotion
class Discount : public Promotion
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
	Discount(): Promotion()
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
	float HandlePromotion(string barcode, float rawPrice, int rawAmount, float newPrice, int newAmount)
	{
		if (m_promotionList.find(barcode) != m_promotionList.end())
		{
			newPrice *= m_promotionList[barcode];
		}
		return Promotion::HandlePromotion(barcode, rawPrice, rawAmount, newPrice, newAmount);
	}
};


// shopping cart, store items that are already selected. cart.txt
class ShoppingCart : public IFileStream
{
private:
	hash_map<string, int> m_cart;// items in cart contains barcode and its amount
protected:
	virtual void HandleItem(string item) // hand items in cart
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

	//************************************
	// @Method   : ApplyPrintSettlement
	// @FullName : ShoppingCart::ApplyPrintSettlement
	// @Access   : public 
	// @Qualifier:
	// @Parameter: ItemList * itemList
	// @Parameter: Item * promotion
	// @Returns  : void
	// @Brief    : apply settlement and print them
	//************************************
	void ApplyPrintSettlement(ItemList *itemList, Item *promotion)
	{
		cout<<"������ϸ    ������	����	С�ƣ�"<<endl;
		float beforePromotion = 0;
		float afterPromotion = 0;
		for (hash_map<string, int>::iterator it = m_cart.begin(); it != m_cart.end(); ++it)
		{
			float price = itemList->GetItemPrice(it->first);
			beforePromotion += it->second * price;
			afterPromotion += promotion->HandlePromotion(it->first, price, it->second, price, it->second);
		}
		cout<<"�ܼƽ��Ż�ǰ  �Żݺ�  �Żݲ�ۣ�"<<endl;
		cout<<"   "<<afterPromotion<<"	    "<<beforePromotion<<"	   "<<afterPromotion<<"	   "<<beforePromotion-afterPromotion<<endl;
	}
};


int _tmain(int argc, _TCHAR* argv[])
{
	ItemList itemList;
	Item item;
	SecondHalfPrice secondHalfPrice;
	Discount discount;
	secondHalfPrice.SetPromotion(&item);
	discount.SetPromotion(&secondHalfPrice);

	Item *pItem = &discount;

	ShoppingCart shoppingCart;
	shoppingCart.ApplyPrintSettlement(&itemList, pItem);

	system("pause");
	return 0;
}

