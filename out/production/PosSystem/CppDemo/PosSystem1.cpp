// PosSystem.cpp : �������̨Ӧ�ó������ڵ㡣
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
		cout<<"������ϸ    ������	����	С�ƣ�"<<endl;
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
		cout<<"�ܼƽ��Ż�ǰ  �Żݺ�  �Żݲ�ۣ�"<<endl;
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

