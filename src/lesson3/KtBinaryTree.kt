package lesson3

import java.lang.AssertionError
import java.lang.RuntimeException
import java.util.*
import kotlin.NoSuchElementException
import kotlin.math.max

// Attention: comparable supported but comparator is not
open class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null

        var parent: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                if (closest.left == null) {
                    closest.left = newNode
                    newNode.parent = closest
                }
                else throw RuntimeException("Unable to add a node")
            }
            else -> {
                if (closest.right == null) {
                    closest.right = newNode
                    newNode.parent = closest
                }
                else throw RuntimeException("Unable to add a node")
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
        root?.let { checkInvariant(it) } ?: true

    override fun height(): Int = height(root)

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    private fun height(node: Node<T>?): Int {
        if (node == null) return 0
        return 1 + max(height(node.left), height(node.right))
    }

    private fun subTreeMinNode(top: Node<T>): Node<T> {
        var node = top
        while (node.left != null) node = node.left!!
        return node
    }

    private fun replace(first: Node<T>, second: Node<T>?) {
        if (first.parent == null) {
            root = second
            return
        }
        val dad = first.parent!!
        when {
            dad.left == null -> dad.right = second
            dad.right == null -> dad.left = second
            else -> {
                if (dad.left!!.value.compareTo(first.value) == 0)
                    dad.left = second
                else
                    dad.right = second
            }
        }
        second?.parent = dad
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        val node = find(element) ?: return false
        if (node.value.compareTo(element) != 0) return false
        when {
            node.left == null -> replace(node, node.right)
            node.right == null -> replace(node, node.left)
            else -> {
                val minNode = subTreeMinNode(node.right!!)
                if (minNode.parent!!.value.compareTo(node.value) != 0) {
                    replace(minNode, minNode.right)
                    minNode.right = node.right
                    minNode.right?.parent = minNode
                }
                replace(node, minNode)
                minNode.left = node.left
                minNode.left?.parent = minNode
            }
        }
        size--
        return true
    }
    // Трудоемкость - O(height)
    // Ресурсоемкость - O(height)

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
        root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    open inner class BinaryTreeIterator internal constructor() : MutableIterator<T> {

        private val iter: Queue<Node<T>> = LinkedList()
        private var cur: Node<T>? = null

        init {
            fun genIterator(state: Node<T>) {
                if (state.left != null) genIterator(state.left!!)
                iter.offer(state)
                if (state.right != null) genIterator(state.right!!)
            }

            if (root != null) {
                genIterator(root!!)
                cur = iter.peek()
            }
        }
        // Трудоемкость построения "итератора" - O(size)
        // Ресурсоемкость - O(size)

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        override fun hasNext(): Boolean = iter.isNotEmpty()
        // Трудоемкость - O(1)
        // Ресурсоемкость - O(1)

        /**
         * Поиск следующего элемента
         * Средняя
         */
        override fun next(): T {
            cur = iter.poll()
            if (cur == null) throw NoSuchElementException()
            return cur!!.value
        }
        // Трудоемкость - O(1)
        // Ресурсоемкость - O(1)

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            remove(cur?.value)
        }
        // Трудоемкость - O(height)
        // Трудоемкость - O(height)
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    private inner class Branch(lowBound: T?, upBound: T?, masterTree: KtBinaryTree<T>): KtBinaryTree<T>() {
        private val lowerBound = lowBound
        private val upperBound = upBound
        private var branchSize = 0
        private val master = masterTree

        private fun T.valid() =
            ((lowerBound == null || this >= lowerBound) && (upperBound == null || this < upperBound))

        override operator fun contains(element: T): Boolean =
            element.valid() && master.contains(element)

        override fun add(element: T): Boolean {
            require(element.valid())
            return master.add(element)
        }

        override fun remove(element: T): Boolean {
            require(element.valid())
            return master.remove(element)
        }

        override var size: Int
            get() = master.count { it.valid() }
            set(value) {
                branchSize = value
            }

        private inner class BranchIterator internal constructor() : BinaryTreeIterator() {
            private val iter: Queue<Node<T>> = LinkedList()
            private var cur: Node<T>? = null

            private fun T.valid() =
                ((lowerBound == null || this >= lowerBound) && (upperBound == null || this < upperBound))

            init {
                fun genIterator(state: Node<T>) {
                    if (state.left != null && state.left!!.value.valid())
                        genIterator(state.left!!)

                    if (state.value.valid()) iter.offer(state)

                    if (state.right != null && state.right!!.value.valid())
                        genIterator(state.right!!)
                }

                if (root != null) {
                    genIterator(root!!)
                    cur = iter.peek()
                }
            }

            override fun hasNext() = iter.isNotEmpty()

            override fun next(): T {
                cur = iter.poll()
                if (cur == null) throw NoSuchElementException()
                return cur!!.value
            }

            override fun remove() {
                master.remove(cur?.value)
            }
        }

        override fun iterator(): MutableIterator<T> = BranchIterator()

    }

    /**
     * Найти множество всех элементов в диапазоне [fromElement, toElement)
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> =
        Branch(fromElement, toElement, this)
    // Трудоемкость - O(master.size)
    // Ресурсоемкость - O(master.size)

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> =
        Branch(null, toElement, this)
    // Трудоемкость - O(master.size)
    // Ресурсоемкость - O(master.size)

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> =
        Branch(fromElement, null, this)
    // Трудоемкость - O(master.size)
    // Ресурсоемкость - O(master.size)

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }

}
