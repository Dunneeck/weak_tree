public class App {
    public static void main(String[] args) {
        //Создадим дерево с корневым элементом 33
        BinaryTree<Integer> tree = new BinaryTree<>(33, null);
        tree.add(5, 35, 1, 20, 4, 17, 31, 99, 18, 19);

        // Сколько элементов
        System.out.println("Колличество элементов - " + tree.size());

        //Распечатаем элементы дерева
        tree.print();
        System.out.println();

        //Удалим корень
        tree.remove(33);
        tree.remove(17);

        System.out.println("Колличество элементов - " + tree.size());
        tree.print();
        
      }
}
