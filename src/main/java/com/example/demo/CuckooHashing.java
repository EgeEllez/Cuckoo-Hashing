package com.example.demo;
import java.util.Arrays;


public class CuckooHashing {
    public final int MAXN;
    public final int ver;
    public final int[][] hashtable;
    public final int[] pos;
    public int collisions;
    public double loadFactor;
    public CuckooHashing(int MAXN, int ver) {
        this.MAXN = MAXN;
        this.ver = ver;
        this.hashtable = new int[ver][MAXN];
        this.pos = new int[ver];
        this.init_table();
        this.collisions = 0;
    }


    private void init_table() {
        for (int i = 0; i < ver; i++) {
            Arrays.fill(hashtable[i], -2147483648);
        }
    }

    private int hash1(int key) {
        return key % MAXN;
    }

    private int hash2(int key) {
        return (key / MAXN) % MAXN;
    }

    private int hash3(int key) {
        return (key / MAXN / MAXN) % MAXN;
    }

    private int hash4(int key) {
        return (key / MAXN / MAXN / MAXN) % MAXN;
    }

    private int hash5(int key) {
        return (key / MAXN / MAXN / MAXN / MAXN) % MAXN;
    }

    private int hash(int function, int key) {
        if (function == 1) {
            return hash1(key);
        } else if (function == 2) {
            return hash2(key);
        } else if (function == 3) {
            return hash3(key);
        } else if (function == 4) {
            return hash4(key);
        } else if (function == 5) {
            return hash5(key);
        }
        return -2147483648;
    }

    private void place(int key, int tableID, int cnt) {

        for (int i = 0; i < ver; i++) {
            pos[i] = hash(i + 1, key);
            if (hashtable[i][pos[i]] == key) {
                return;
            }

        }

        int dis = hashtable[tableID][pos[tableID]];
        hashtable[tableID][pos[tableID]] = key;

        if (dis != -2147483648) {
            collisions++;
            place(dis, (tableID + 1) % ver, cnt + 1);
        }
    }

    public void insert(int key) {
        place(key, 0, 0);
        loadFactor = (double) (countElements() / MAXN);
    }
    private int countElements(){
        int count = 0;
        for (int i = 0; i < ver; i++) {
            for (int j = 0; j < MAXN; j++) {
                if (hashtable[i][j] != -2147483648) {
                    count++;
                }
            }
        }
        return count;
    }


    public boolean search(int key) {
        for (int i = 0; i < ver; i++) {
            int pos = hash(i + 1, key);
            if (hashtable[i][pos] == key) {
                return true;
            }
        }
        return false;
    }

    public boolean delete(int key) {
        for (int i = 0; i < ver; i++) {
            int pos = hash(i + 1, key);
            if (hashtable[i][pos] == key) {
                hashtable[i][pos] = -2147483648;
                return true;
            }
        }
        return false;
    }

    public void print_table() {
        System.out.println("Final hash tables:");
        for (int i = 0; i < ver; i++) {
            System.out.println("Table " + (i + 1) + ":");
            for (int j = 0; j < MAXN; j++) {
                if (hashtable[i][j] == -2147483648) {
                    System.out.print("None ");
                } else {
                    System.out.print(hashtable[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }


}





