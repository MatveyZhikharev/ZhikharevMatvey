enum AnimalType {
  Predator,
  Herbivorous;
}

enum MoveType {
  flying,
  walking,
  swimming;
}

interface Animal {
  void eat(String meal);

  void move();
}

class Zoo {
  protected String animal;
  protected MoveType moveType;
  protected AnimalType animalType;

  public Zoo(String animal, MoveType moveType, AnimalType animalType) {
    this.animal = animal;
    this.moveType = moveType;
    this.animalType = animalType;
  }
}

class Horse extends Zoo implements Animal {
  public Horse(String animal, MoveType moveType, AnimalType animalType) {
    super(animal, moveType, animalType);
  }

  @Override
  public void eat(String meal) {
    if (meal.equals("Grass")) {
      System.out.println(animal + " is eating");
    } else {
      System.out.println(animal + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void move() {
    System.out.println("Horse is " + moveType);
  }
}

class Camel extends Zoo implements Animal {
  public Camel(String animal, MoveType moveType, AnimalType animalType) {
    super(animal, moveType, animalType);
  }

  @Override
  public void eat(String meal) {
    if (meal.equals("Grass")) {
      System.out.println(animal + " is eating");
    } else {
      System.out.println(animal + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void move() {
    System.out.println("Camel is " + moveType);
  }
}

class Tiger extends Zoo implements Animal {
  public Tiger(String animal, MoveType moveType, AnimalType animalType) {
    super(animal, moveType, animalType);
  }

  @Override
  public void eat(String meal) {
    if (meal.equals("Beef")) {
      System.out.println(animal + " is eating");
    } else {
      System.out.println(animal + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void move() {
    System.out.println("Tiger is " + moveType);
  }
}

class Dolphin extends Zoo implements Animal {
  public Dolphin(String animal, MoveType moveType, AnimalType animalType) {
    super(animal, moveType, animalType);
  }

  @Override
  public void eat(String meal) {
    if (meal.equals("Fish")) {
      System.out.println(animal + " is eating");
    } else {
      System.out.println(animal + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void move() {
    System.out.println("Dolphin is " + moveType);
  }
}

class Eagle extends Zoo implements Animal {
  public Eagle(String animal, MoveType moveType, AnimalType animalType) {
    super(animal, moveType, animalType);
  }

  @Override
  public void eat(String meal) {
    if (meal.equals("Meat")) {
      System.out.println(animal + " is eating");
    } else {
      System.out.println(animal + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void move() {
    System.out.println("Eagle is " + moveType);
  }
}

public class Main {
  public static void main(String[] args) {
    Eagle eagle = new Eagle("Арёл", MoveType.flying, AnimalType.Predator);
    eagle.eat("Grass");
    eagle.eat("Meat");
    eagle.move();
    Camel camel = new Camel("CameЦ", MoveType.walking, AnimalType.Predator);
    camel.eat("Beef");
    camel.move();
    Tiger tiger = new Tiger("Тигр", MoveType.walking, AnimalType.Predator);
    tiger.eat("Beef");
    tiger.move();
  }
}
