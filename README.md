
# PEPSE Project

Welcome to the PEPSE Project! This project packaged based  is designed to generate and manage various aspects of tree creation and behavior in a game environment. It utilizes several design patterns to ensure modularity, reusability, and maintainability.

## General Description

This is a virtual reality project that recreates a Mario-like game world. This immersive environment includes vibrant landscapes, interactive objects, and dynamic elements that mimic the beloved Mario game universe. Players can explore this virtual world, interacting with various components such as trees, fruits, and other environmental objects, enhancing the gaming experience with realistic and engaging features.

## Description

The Tree Package is a comprehensive solution for creating and managing trees in a virtual environment. It includes classes for generating tree components such as trunks, leaves, and fruits, and handles interactions with these components. The package leverages design patterns like Observer and Facade to facilitate interaction and communication between different parts of the system.

## Classes Overview

### Flora
- **Description:** The central class of the package.
- **Methods:** 
  - Receives a start point and an end point and fills the range with trees.

### Tree
- **Description:** A static class that produces a tree and returns a list of lists, each containing a different part of the tree (trunk, leaves, and fruit).
- **Methods:**
  - Creates a tree and returns it.

### Trunk
- **Description:** A static class that creates a stem at a given location and determines its behavior.

### CreateFruit
- **Description:** A static class responsible for producing fruit at a given location and determining its behavior.

### Leaf
- **Description:** A class that creates a leaf at a given location and determines its behavior.

### Fruit
- **Description:** Inherits from GameObject and overrides its `onCollisionEnter` method. The new behavior adds energy to the avatar when it collides with a fruit.

## Class Relationships

- The **Flora** class calls the **Tree** class.
- The **Tree** class calls the **Trunk**, **Leaf**, and **CreateFruit** classes.
- The **CreateFruit** class creates an instance of the **Fruit** class.
- The **Leaf**, **Trunk**, and **Fruit** classes receive the callbacks they need through **Flora** and **Tree** from the manager and do not know the avatar directly.

## Design Patterns

### Observer Pattern
- Used in the **Avatar** class. When the `jump` method is called, the avatar notifies all observers.

### Facade Pattern
- Used in the **Flora** class, which is the only class the user interacts with. **Flora** is responsible for creating trees, and trees are responsible for creating the parts of the tree.

## Randomization

- The **Random** class is used to introduce variability between games.

## How to Use

1. **Initialize Flora:**
   ```java
   Flora flora = new Flora();
   flora.fillRange(startPoint, endPoint);
   ```

2. **Create a Tree:**
   ```java
   Tree tree = Tree.createTree();
   ```

3. **Generate Components:**
   - **Trunk:** `Trunk.create(location);`
   - **Leaf:** `Leaf.create(location);`
   - **Fruit:** `CreateFruit.create(location);`

## Assets

- The package includes various sprite assets located in the `src/assets` directory. These assets represent different states and actions of the game objects.

## Installation

To include the Tree Package in your project, copy the `pepse` directory into your project's source directory.

## Contact

For more information or to contribute, please contact [Your Name] at [Your Email].

---

This package demonstrates advanced object-oriented design and implementation skills, making it a valuable addition to any game development project. Thank you for considering my work!
