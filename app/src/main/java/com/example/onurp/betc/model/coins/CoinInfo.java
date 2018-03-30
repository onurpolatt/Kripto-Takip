package com.example.onurp.betc.model.coins;

import java.io.Serializable;

public class CoinInfo implements Serializable {
    private String ProofType;

    private String TotalCoinSupply;

    private String CoinName;

    private String SortOrder;

    private String ImageUrl;

    private String Algorithm;

    private String IsTrading;

    private String Name;

    private String Url;

    private String Symbol;

    private String FullName;

    private String Sponsored;

    private String Id;

    private String TotalCoinsFreeFloat;

    private String PreMinedValue;

    private String FullyPremined;

    public CoinInfo(String imageUrl, String fullName) {
        this.ImageUrl = imageUrl;
        this.FullName = fullName;
    }

    public String getProofType ()
    {
        return ProofType;
    }

    public void setProofType (String ProofType)
    {
        this.ProofType = ProofType;
    }

    public String getTotalCoinSupply ()
    {
        return TotalCoinSupply;
    }

    public void setTotalCoinSupply (String TotalCoinSupply)
    {
        this.TotalCoinSupply = TotalCoinSupply;
    }

    public String getCoinName ()
    {
        return CoinName;
    }

    public void setCoinName (String CoinName)
    {
        this.CoinName = CoinName;
    }

    public String getSortOrder ()
    {
        return SortOrder;
    }

    public void setSortOrder (String SortOrder)
    {
        this.SortOrder = SortOrder;
    }

    public String getImageUrl ()
    {
        return ImageUrl;
    }

    public void setImageUrl (String ImageUrl)
    {
        this.ImageUrl = ImageUrl;
    }

    public String getAlgorithm ()
    {
        return Algorithm;
    }

    public void setAlgorithm (String Algorithm)
    {
        this.Algorithm = Algorithm;
    }

    public String getIsTrading ()
    {
        return IsTrading;
    }

    public void setIsTrading (String IsTrading)
    {
        this.IsTrading = IsTrading;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getUrl ()
    {
        return Url;
    }

    public void setUrl (String Url)
    {
        this.Url = Url;
    }

    public String getSymbol ()
    {
        return Symbol;
    }

    public void setSymbol (String Symbol)
    {
        this.Symbol = Symbol;
    }

    public String getFullName ()
    {
        return FullName;
    }

    public void setFullName (String FullName)
    {
        this.FullName = FullName;
    }

    public String getSponsored ()
    {
        return Sponsored;
    }

    public void setSponsored (String Sponsored)
    {
        this.Sponsored = Sponsored;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getTotalCoinsFreeFloat ()
    {
        return TotalCoinsFreeFloat;
    }

    public void setTotalCoinsFreeFloat (String TotalCoinsFreeFloat)
    {
        this.TotalCoinsFreeFloat = TotalCoinsFreeFloat;
    }

    public String getPreMinedValue ()
    {
        return PreMinedValue;
    }

    public void setPreMinedValue (String PreMinedValue)
    {
        this.PreMinedValue = PreMinedValue;
    }

    public String getFullyPremined ()
    {
        return FullyPremined;
    }

    public void setFullyPremined (String FullyPremined)
    {
        this.FullyPremined = FullyPremined;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ProofType = "+ProofType+", TotalCoinSupply = "+TotalCoinSupply+", CoinName = "+CoinName+", SortOrder = "+SortOrder+", ImageUrl = "+ImageUrl+", Algorithm = "+Algorithm+", IsTrading = "+IsTrading+", Name = "+Name+", Url = "+Url+", Symbol = "+Symbol+", FullName = "+FullName+", Sponsored = "+Sponsored+", Id = "+Id+", TotalCoinsFreeFloat = "+TotalCoinsFreeFloat+", PreMinedValue = "+PreMinedValue+", FullyPremined = "+FullyPremined+"]";
    }
}
