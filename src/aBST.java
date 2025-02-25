import java.util.*;

class aBST
{
    public Integer Tree []; // массив ключей

    public aBST(int depth)
    {
        int tree_size = (int)Math.pow(2, depth + 1) - 1;
        Tree = new Integer[ tree_size ];
        for(int i=0; i<tree_size; i++) Tree[i] = null;
    }

    public Integer FindKeyIndex(int key)
    {
        for (int i = 0; i < Tree.length; i = key < Tree[i] ? 2 * i + 1 : 2 * i + 2) {
            if (Tree[i] == null)
                return -i;
            if (key == Tree[i])
                return i;
        }
        return null;
    }

    public int AddKey(int key)
    {
        Integer WhereNeedAdd = FindKeyIndex(key);
        if (WhereNeedAdd == null)
            return  -1;
        if (WhereNeedAdd > 0)
            return WhereNeedAdd;
        if (WhereNeedAdd < 0)
        {
            Tree[-WhereNeedAdd] = key;
        }
        if (Tree[0] == null)
            Tree[0] = key;
        return 0;
    }

}