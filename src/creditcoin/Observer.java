/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Observer 
{
      private final StringProperty yPosition = new SimpleStringProperty(this,"yPosition","");
      private final StringProperty neighbour = new SimpleStringProperty(this,"neighbour","");
      private final StringProperty speed = new SimpleStringProperty(this,"speed","");
      private final StringProperty xPosition = new SimpleStringProperty(this,"xPosition","");
      private final StringProperty data = new SimpleStringProperty(this,"data","");
      private final StringProperty count = new SimpleStringProperty(this,"count","");
    private final StringProperty creditCoin = new SimpleStringProperty(this,"creditCoin","");

    public String getCreditCoin() {
        return creditCoin.get();
    }

    public void setCreditCoin(String value) {
        creditCoin.set(value);
    }

    public StringProperty creditCoinProperty() {
        return creditCoin;
    }

    public String getCount() {
        return count.get();
    }

    public void setCount(String value) {
        count.set(value);
    }

    public StringProperty countProperty() {
        return count;
    }
      
      
      
    public String getData() {
        return data.get();
    }

    public void setData(String value) {
        data.set(value);
    }

    public StringProperty dataProperty() {
        return data;
    }
      
      
      

    public String getxPosition() {
        return xPosition.get();
    }

    public void setxPosition(String value) {
        xPosition.set(value);
    }

    public StringProperty xPositionProperty() {
        return xPosition;
    }
     

    public String getNeighbour() {
        return neighbour.get();
    }

    public void setNeighbour(String value) {
        neighbour.set(value);
    }

    public StringProperty neighbourProperty() {
        return neighbour;
    }
  

    public String getyPosition() {
        return yPosition.get();
    }

    public void setyPosition(String value) {
        yPosition.set(value);
    }

    public StringProperty yPositionProperty() {
        return yPosition;
    }

    

    public String getSpeed() {
        return speed.get();
    }

    public void setSpeed(String value) {
        speed.set(value);
    }

    public StringProperty speedProperty() {
        return speed;
    }
      
}
