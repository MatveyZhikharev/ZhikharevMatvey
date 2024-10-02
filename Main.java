enum AnimalType {
  Predator,
  Herbivorous;
}

enum MoveType {
  flying,
  walking,
  swimming;
}

abstract class Animal {
  protected String name;

  public Animal(String name) {
    this.name = name;
  }
}

abstract class Predator extends Animal {
  public Predator(String name) {
    super(name);
  }

  public void killFood() {
    System.out.println(this.name + " killed a herbivorous for a breakfast");
  }
}

abstract class Herbivorous extends Animal {
  public Herbivorous(String name) {
    super(name);
  }
}

interface flyable {
  void fly();
}

interface walkable {
  void walk();
}

interface swimmable {
  void swim();
}

class Horse extends Herbivorous implements walkable {
  public Horse(String name) {
    super(name);
  }

  public void eat(String meal) {
    if (meal.equals("Grass")) {
      System.out.println(name + " is eating");
    } else {
      System.out.println(name + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void walk() {
    System.out.println("tigidik-tigidik-tigidik");
  }
}

class Camel extends Herbivorous implements walkable {
  public Camel(String name) {
    super(name);
  }

  public void eat(String meal) {
    if (meal.equals("Grass")) {
      System.out.println(name + " is eating");
    } else {
      System.out.println(name + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void walk() {
    System.out.println("camel makes tigidik-tigidik-tigidik");
  }
}

class Tiger extends Predator implements walkable {
  public Tiger(String name) {
    super(name);
  }

  public void eat(String meal) {
    if (meal.equals("Beef")) {
      System.out.println(name + " is eating");
    } else {
      System.out.println(name + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void walk() {
    System.out.println("Tiger is walking");
  }
}

class Dolphin extends Predator implements swimmable {
  public Dolphin(String name) {
    super(name);
  }

  public void eat(String meal) {
    if (meal.equals("Fish")) {
      System.out.println(name + " is eating");
    } else {
      System.out.println(name + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void swim() {
    System.out.println("Dolphin is swimming whoooooshhhhhhhhhhbllbll");
  }
}

class Eagle extends Predator implements flyable {
  public Eagle(String name) {
    super(name);
  }

  public void eat(String meal) {
    if (meal.equals("Meat")) {
      System.out.println(name + " is eating");
    } else {
      System.out.println(name + " doesn't eat " + meal + "😥");
    }
  }

  @Override
  public void fly() {
    System.out.println("Eagle is flying HEEEEEEEEEEEEEEEEEEEEEEEEEEEOW");
  }
}

public class Main {
  public static void main(String[] args) {
    Eagle eagle = new Eagle("Арёл");
    eagle.eat("Grass");
    eagle.eat("Meat");
    eagle.fly();
    Camel camel = new Camel("CameЦ");
    camel.eat("Beef");
    camel.walk();
    Tiger tiger = new Tiger("Тигр");
    tiger.eat("Beef");
    tiger.walk();
  }
}
