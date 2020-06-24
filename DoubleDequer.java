class DoubleDequer{
  public static void main(String[] args)  {
    Deque<String> deck = new Deque<String>();

    System.out.println(deck.isEmpty()); //Empty is true, cause its empty

    deck.enqueueFront("Jack of Hearts"); // first thing to enqueue at front
    deck.enqueueFront("Queen of Hearts"); // second thing to enqueue at front
    deck.enqueueRear("King of Spades"); // first thing to enqueue at rear
    deck.enqueueRear("Ace of Spades"); // first thing to enqueue at rear

    System.out.println(deck.isEmpty());// false now

    System.out.println(deck.dequeueFront());// print Queen cause its first to get dequeued from front
    System.out.println(deck.dequeueRear());// prints Ace cause its first to get dequeued from rear
    System.out.println(deck.dequeueFront());// print Jack cause its next thing to get dequeued from front
    System.out.println(deck.dequeueRear());// print King cause its next thing to get dequeued from rear
  }
}

class Deque<Base>{
  private class Node  {
    private Base object;
    private Node left;
    private Node right;

    private Node(Base object,Node left,Node right)
    {
      this.object = object;
      this.left = left;
      this.right = right;
    }  }
    private Node head;

    public Deque()    {
      head = new Node(null,null,null);
      head.left = head;
      head.right = head;
    }

    public void enqueueFront(Base object)    {
      Node dot = new Node(object,head,head.right);
      head.right.left = dot;
      head.right = dot;
    }

    public void enqueueRear(Base object)    {
      Node dot = new Node(object,head.left,head);
      head.left.right = dot;
      head.left = dot;
    }

    public Base dequeueFront()    {
      if(isEmpty())     {
        throw new IllegalStateException("cannot deque from front");
      }
      Base temp = head.right.object;
      head.right.right.left = head;
      head.right = head.right.right;
      return temp;
    }

    public Base dequeueRear()    {
      if(isEmpty())      {
        throw new IllegalStateException("cannot deque from rear");
      }
      Base temp = head.left.object;
      head.left.left.right = head;
      head.left = head.left.left;
      return temp;
    }

    public boolean isEmpty()    {
    return((head.left.equals(head))&&(head.right.equals(head)));
  }
}
