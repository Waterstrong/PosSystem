// PosSystem3.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"

#include <iostream>
#include <fstream>
#include <sstream>
#include <hash_map>
#include <hash_set>
#include <list>
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

class PromotionStrategy;
// promotion activity, super class
class Promotion
{
protected:
	PromotionStrategy *m_promotionStrategy;
public:
	Promotion(PromotionStrategy &strategy)
	{
		m_promotionStrategy = &strategy;
	}
	//************************************
	// Method   : AcceptPromotion
	// FullName : Promotion::AcceptPromotion
	// Access   : virtual public 
	// Qualifier:
	// Parameter: Item &
	// Returns  : void
	// Brief    : handle specific promotion and return the promotion of wholes
	//************************************
	virtual void AcceptPromotion(Item&) = 0;
	//************************************
	// Method   : Offline
	// FullName : Promotion::Offline
	// Access   : virtual public 
	// Qualifier:
	// Returns  : void
	// Brief    : promotion offline
	//************************************
	virtual void Offline() = 0;

};



// promotion strategy in all
class PromotionStrategy
{
protected:
	hash_map<string, list<Promotion*> > m_promotions;
public:
	//************************************
	// Method   : AcceptPromotion
	// FullName : PromotionStrategy::AcceptPromotion
	// Access   : public 
	// Qualifier:
	// Parameter: Item item
	// Returns  : Item
	// Brief    : accept the promotion and return the new item
	//************************************
	Item AcceptPromotion(Item item)
	{
		if (m_promotions.find(item.GetBarcode()) != m_promotions.end())
		{
			list<Promotion*> promotion = m_promotions[item.GetBarcode()];
			for (list<Promotion*>::iterator it = promotion.begin(); it != promotion.end(); ++it)
			{
				(*it)->AcceptPromotion(item);
			}
		}
		return item;
	}
	void PushPromotion(string barcode, Promotion* promotion)
	{
		m_promotions[barcode].push_back(promotion);
	}
	void PopPromotion(string barcode, Promotion* promotion)
	{
		if (m_promotions.find(barcode) != m_promotions.end())
		{
			m_promotions[barcode].remove(promotion);
			if (m_promotions[barcode].empty())
			{
				m_promotions.erase(barcode);
			}
		}
	}
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
		m_promotionStrategy->PushPromotion(item, this); // add this to the strategy list
	}
public:
	SecondHalfPrice(PromotionStrategy &strategy):Promotion(strategy)
	{
		LoadItemFromFile("second_half_price_promotion.txt");
		m_discountStandard = 2;
		m_discountDegree = 0.5;
	}
	void Offline()
	{
		for (hash_set<string>::iterator it = m_promotionList.begin(); it != m_promotionList.end(); ++it)
		{
			m_promotionStrategy->PopPromotion(*it, this);
		}
	}
	void AcceptPromotion(Item& item)
	{
		int discountAmount = item.GetAmount() / m_discountStandard;
		float totalPrice = item.GetPrice() * m_discountDegree * discountAmount + (item.GetAmount()-discountAmount)*item.GetPrice();
		item.SetPrice(totalPrice / item.GetAmount());
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
			string barcode = item.substr(0, pos);
			float discount = Str2Num<float>(item.substr(pos+1))/100;
			m_promotionList.insert(make_pair( barcode , discount) );
			m_promotionStrategy->PushPromotion(barcode, this); // add this to the strategy list
		}
		else
		{
			ErrorDirect("Discount item parsed error.");
		}
	}
public:
	Discount(PromotionStrategy &strategy):Promotion(strategy)
	{
		LoadItemFromFile("discount_promotion.txt");
	}
	void Offline()
	{
		for (hash_map<string, float>::iterator it = m_promotionList.begin(); it != m_promotionList.end(); ++it)
		{
			m_promotionStrategy->PopPromotion(it->first, this);
		}
	}
	void AcceptPromotion(Item& item)
	{
		item.SetPrice(item.GetPrice() * m_promotionList[item.GetBarcode()]);
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
	//************************************
	// Method   : ApplyPrintSettlement
	// FullName : ShoppingCart::ApplyPrintSettlement
	// Access   : public 
	// Qualifier:
	// Parameter: ItemList * itemList
	// Parameter: PromotionStrategy * promotionStrategy
	// Returns  : void
	// Brief    : apply the settlement and print
	//************************************
	void ApplyPrintSettlement(ItemList *itemList, PromotionStrategy *promotionStrategy)
	{
		cout<<"购物明细    （数量	单价	小计）"<<endl;
		float beforePromotion = 0;
		float afterPromotion = 0;
		for (hash_map<string, int>::iterator it = m_cart.begin(); it != m_cart.end(); ++it)
		{
			float price = itemList->GetItemPrice(it->first);
			beforePromotion += it->second * price;
			Item item(it->first, price, it->second);
			Item newItem = promotionStrategy->AcceptPromotion(item);
			float subtotal = newItem.GetSubtotal();
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

	PromotionStrategy promotionStrategy;

	SecondHalfPrice secondHalfPrice(promotionStrategy);
	Discount discount(promotionStrategy);

	ShoppingCart shoppingCart;
	shoppingCart.ApplyPrintSettlement(&itemList, &promotionStrategy);

	cout<<"Second Settlement test:"<<endl;
	//discount.Offline();
	secondHalfPrice.Offline();
	shoppingCart.ApplyPrintSettlement(&itemList, &promotionStrategy);

	system("pause");
	return 0;
}

