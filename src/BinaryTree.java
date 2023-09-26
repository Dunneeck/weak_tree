import java.util.ArrayList;
import java.util.List;

/**
 * реализация спизж... моего бинарного дерева
 * Функционал: 
 * 1)добавление елемента;
 * 2)удаление елемента;
 * 3)вывод дерева;
 * 4)количество элементов дерева;
 */
public class BinaryTree<T extends Comparable<T>> {
    private T val;
    private BinaryTree left;
    private BinaryTree right;
    private BinaryTree parent;
    private List<T> listForPrint = new ArrayList<>();
    public T val() {
        return val;
    }
    public BinaryTree left() {
        return left;
    }
    public BinaryTree right() {
        return right;
    }
    public BinaryTree parent() {
        return parent;
    }
    /**
     * конструктор бинарного дерева
     * @param val добавление элемента(будет корнем)
     * @param parent является ли родетелем
     */
    public BinaryTree(T val, BinaryTree parent) {
        this.val = val;
        this.parent = parent;
    }
    /**
     * добавление нескольких элементов в дерево(1, 2, 3 , и тд)
     * @param vals добавление элементов
     */
    public void add(T...vals){
        for(T v : vals){
            add(v);
        }
    }
    /**
     * добавление элемента в дерево
     * @param val значение элемента
     */
    public void add(T val){
        if(val.compareTo(this.val) < 0){
            if(this.left==null){
                this.left = new BinaryTree(val, this);
            }
            else if(this.left != null)
                this.left.add(val);
        }
        else{
            if(this.right==null){
                this.right = new BinaryTree(val, this);
            }
            else if(this.right != null)
                this.right.add(val);
        }
    }
    /**
     * @param tree
     * @param val
     * @return
     */
    private BinaryTree<T> _search(BinaryTree<T> tree, T val){
        if(tree == null) return null;
        switch (val.compareTo(tree.val)){
            case 1: return _search(tree.right, val);
            case -1: return _search(tree.left, val);
            case 0: return tree;
            default: return null;
        }
    }
    /**
     * поиск элемента по значению
     * @param val элемент для поиска
     * @return если val есть в дереве ? возвращает его значение : null
     */
    public BinaryTree<T> search(T val){
        return _search(this, val);
    }
    /**
     * удаление элемента с дерева
     * @param val элемент на удаление
     * @return true - если эл.. удалён, fasle - эл. отсутствует
     */
    public boolean remove(T val){
        //Проверяем, существует ли данный узел
        BinaryTree<T> tree = search(val);
        if(tree == null){
            //Если узла не существует, вернем false
            return false;
        }
        BinaryTree<T> curTree;
        //Если удаляем корень
        if(tree == this){
            if(tree.right!=null) {
                curTree = tree.right;
            }
            else curTree = tree.left;
            while (curTree.left != null) {
                curTree = curTree.left;
            }
            T temp = curTree.val;
            this.remove(temp);
            tree.val = temp;
            return true;
        }
        //Удаление листьев
        if(tree.left==null && tree.right==null && tree.parent != null){
            if(tree == tree.parent.left)
                tree.parent.left = null;
            else {
                tree.parent.right = null;
            }
            return true;
        }
        //Удаление узла, имеющего левое поддерево, но не имеющее правого поддерева
        if(tree.left != null && tree.right == null){
            //Меняем родителя
            tree.left.parent = tree.parent;
            if(tree == tree.parent.left){
                tree.parent.left = tree.left;
            }
            else if(tree == tree.parent.right){
                tree.parent.right = tree.left;
            }
            return true;
        }
        //Удаление узла, имеющего правое поддерево, но не имеющее левого поддерева
        if(tree.left == null && tree.right != null){
            //Меняем родителя
            tree.right.parent = tree.parent;
            if(tree == tree.parent.left){
                tree.parent.left = tree.right;
            }
            else if(tree == tree.parent.right){
                tree.parent.right = tree.right;
            }
            return true;
        }
        //Удаляем узел, имеющий поддеревья с обеих сторон
        if(tree.right!=null && tree.left!=null) {
            curTree = tree.right;
            while (curTree.left != null) {
                curTree = curTree.left;
            }
            //Если самый левый элемент является первым потомком
            if(curTree.parent == tree) {
                curTree.left = tree.left;
                tree.left.parent = curTree;
                curTree.parent = tree.parent;
                if (tree == tree.parent.left) {
                    tree.parent.left = curTree;
                } else if (tree == tree.parent.right) {
                    tree.parent.right = curTree;
                }
                return true;
            }
            //Если самый левый элемент НЕ является первым потомком
            else {
                if (curTree.right != null) {
                    curTree.right.parent = curTree.parent;
                }
                curTree.parent.left = curTree.right;
                curTree.right = tree.right;
                curTree.left = tree.left;
                tree.left.parent = curTree;
                tree.right.parent = curTree;
                curTree.parent = tree.parent;
                if (tree == tree.parent.left) {
                    tree.parent.left = curTree;
                } else if (tree == tree.parent.right) {
                    tree.parent.right = curTree;
                }
                return true;
            }
        }
        return false;
    }

    private void _print(BinaryTree<T> node){
        if(node == null) return;
        _print(node.left);
        listForPrint.add(node.val);
        System.out.print(node + " ");
        if(node.right!=null)
            _print(node.right);
    }
    /**
     * вывод бинарного дерева в строку с помощью списка 
     */
    public void print(){
        listForPrint.clear();
        _print(this);
        System.out.println();
    }

    private int _size(BinaryTree<T> current){
        if(current == null) return 0;
        
        return 1 + _size(current.left) + _size(current.right);
    }
    /**
     * подсчет колличества элементов в дереве
     * @return количесво элеметнов(int)
     */
    public int size(){
        return _size(this);
    }
    @Override
    public String toString() {
        return val.toString();
    }
}
