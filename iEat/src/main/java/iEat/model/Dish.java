package iEat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dish {

    @Id
    @GeneratedValue
    private int id;
	private String name;
	private String cook;
	
	public Dish(){
		
	}
	
	public Dish(String name, String cook, int id) {
		super();
		this.name = name;
		this.cook = cook;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCook() {
		return cook;
	}

	public void setCook(String cook) {
		this.cook = cook;
	}


    @Override
    public boolean equals(Object object) {
        if (object instanceof Dish){
            Dish dish = (Dish) object;
            return dish.id == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
