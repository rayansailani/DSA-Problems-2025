import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
     class LinkedListNode {
         public int data;
         public LinkedListNode next;
         public LinkedListNode(int data) {
             this.data = data;
             this.next = null;
         }
     }


    public static void main (String[] args)
    {
        System.out.println(findRepeatedDnaSequences("ACGTACGTACGTACGTACGTACGTACGTACGT"));
    }

    public static String reverseWords(String sentence) {
        sentence = sentence.replaceAll("\\s+", " ").trim();
        String result = "";
        int sentenceLength = sentence.length();
        int start = -1, end = -1;
        for(int i = sentenceLength - 1; i>=0;i--) {
            // setting starter and ending pointers
            if(sentence.charAt(i) != ' ' && start == -1) {
                start = i + 1;
                if(i == 0) {
                    end = i;
                }
            } else if(sentence.charAt(i) == ' ' && start != -1) {
                end = i + 1;
            } else if(sentence.charAt(i) != ' ' && start != -1 && i == 0) {
                end = i;
            }
            if(start != -1 && end != -1) {
                result += sentence.substring(end, start);
                if(i != 0)
                    result += " ";
                start = -1;
                end = -1;
            }
        }
        return result;
    }

    public static int findLongestSubstring(String str) {
        if (str.length() <= 1) {
            return str.length();
        }
        int windowSize = 0, start = 0;
        Map<Character, Integer> mem = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            if (mem.containsKey(str.charAt(i))) {
                // setting the start to the next value that appears in the hashmap
                start = Math.max(start,
                                 mem.get(str.charAt(i)) + 1);
            }
            mem.put(str.charAt(i),
                    i);
            windowSize = Math.max(windowSize,
                                  i - start + 1);
        }
        return windowSize;
    }

    public static int longestRepeatingCharacterReplacement(String s, int k) {
        int start = 0, end = 0;
        int maxFreq = 0;
        Map<Character, Integer> mem = new HashMap<>();
        int longestSubstring = 0;
        for(int i = 0; i<s.length();i++) {
            Character c = s.charAt(i);
            mem.put(c, mem.getOrDefault(c, 0)  + 1);
            maxFreq = Math.max(maxFreq, mem.get(c));

            if(i - start + 1 - maxFreq > k) {
                mem.put(s.charAt(start), mem.get(c) - 1);
                start++;
            }

            longestSubstring = Math.max(longestSubstring, i - start + 1);
        }
        return longestSubstring;
    }

    public static List<String> findRepeatedDnaSequences(String s) {
        Set<String> result = new HashSet<>();
        if(s.length() <= 10) {
            return new ArrayList<>();
        }
        int start = 0, end = 9;
        Map<String, Integer> mem = new HashMap<>();
        String originalString = s.substring(start,end);
        mem.put(originalString, 1);
        for(int i = end; i<s.length();i++) {
            String newString = s.substring(start++, i+1);
            if(mem.containsKey(newString)) {
                result.add(newString);
                mem.put(newString, mem.get(newString)+1);
            } else {
                mem.put(newString, 1);
            }
        }
        return new ArrayList<>(result);
    }

    /**
     * Starting with the given number
     * n
     * n
     * , replace the number with the sum of the squares of its digits.
     * Repeat the process until:
     * The number equals
     * 1
     * 1
     * , which will depict that the given number
     * n
     * n
     *  is a happy number.
     * The number enters a cycle, which will depict that the given number
     * n
     * n
     *  is not a happy number.
     * @param n
     * @return
     */
    public static boolean isHappyNumber(int n) {
        int slow = n, fast = sumOfSquaredDigits(n);
        while(fast != 1 && slow != fast) {
            fast = sumOfSquaredDigits(sumOfSquaredDigits(fast));
            slow = sumOfSquaredDigits(slow);
        }
        return fast == 1;
    }

    public static int sumOfSquaredDigits(int n) {
        int number = n, total = 0;
        while(number > 0) {
            int digit = number%10;
            number = number/10;
            total+=Math.pow(digit,2);
        }
        return total;
    }

    /**
     * Checking if the linkedlist is a pallindrome
     * @param head
     * @return
     */
    public static boolean palindrome(LinkedListNode head) {

        // Replace this placeholder return statement with your code
        LinkedListNode fast = head, slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // slow is at the mid position
        // 1. reverse the second part of the array
        // [] -> [] -> [] -> [points to null] <- [] <- [] <- []
        LinkedListNode reversedHead = reverseLinkedList(slow);
        // 2. check if the linked lists are same
        boolean isSameList = checkIfLinkedListsSame(head, reversedHead);
        // 3. reverse back the list now
        // // [] -> [] -> [] -> [] -> [] -> [] -> [points to null]
        reverseLinkedList(reversedHead);
        return isSameList;
    }

    public static LinkedListNode reverseLinkedList(LinkedListNode head) {
        LinkedListNode current = head, previous = null, next = null;
        while(current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    public static boolean checkIfLinkedListsSame(LinkedListNode h1, LinkedListNode h2) {
        while(h1 != null && h2 != null) {
            if(h1.data != h2.data) {
                return false;
            } else {
                h1 = h1.next;
                h2 = h2.next;
            }
        }
        return true;
    }

    public static boolean circularArrayLoop(int[] nums) {
        for(int i = 0; i<nums.length; i++) {
            int slow = i, fast = i;
            boolean forward = nums[i] >= 0;
            int count = 0;
            while(true) {
                slow = findIndex(nums, slow, nums[slow]);
                if(isNotCycle(nums, forward, slow)) {
                    break;
                }
                fast = findIndex(nums, fast, nums[fast]);
                fast = findIndex(nums, fast, nums[fast]);
                if(isNotCycle(nums, forward, fast)) {
                    break;
                }
                count++;
                if(fast == slow && count >= 2)
                    return true;
            }
        }
        return false;
    }

    public static boolean isNotCycle(int[] nums, boolean prevDirection, int pointer) {
        // Set current direction to true if current element is positive, set false otherwise.
        boolean currDirection = nums[pointer] >= 0;

        // If current direction and previous direction are different or moving a pointer takes back to the same value,
        // then the cycle is not possible, we return true, otherwise return false.
        if (prevDirection != currDirection || nums[pointer] % nums.length == 0) {
            return true;
        }

        return false;
    }

    public static int findIndex(int[] nums, int from, int distance) {
         int result = (from + distance) % nums.length;
         if(result < 0)
             result += nums.length;
         return result;
    }

    public static LinkedListNode removeNthLastNode(LinkedListNode head, int n) {
            LinkedListNode fast = head, slow = head;

            // move the fast pointer towards n steps first
            for(int i = 0;i<n; i++) {
                fast = fast.next;
            }

            if(fast == null) {
                head = head.next;
                return head;
            }

            while(fast.next != null) {
                fast = fast.next;
                slow = slow.next;
            }

            slow.next = slow.next.next;
            return head;
     }

    public static int[] sortColors (int[] colors) {
         // zp marks the pointer where the ones are correctly placed before
         // tp marks the pointer where the twos are correctly placed after
         int ptr = 0, zp = 0, tp = colors.length - 1;
         while(ptr <= tp) {
             if(colors[ptr] == 0) {
                 // swap with zeroes index
                 int temp = colors[ptr];
                 colors[ptr] = colors[zp];
                 colors[zp] = temp;
                 zp++;
                 ptr++;
             } else if(colors[ptr] == 1) {
                 ptr++;
             } else {
                 int temp = colors[ptr];
                 colors[ptr] = colors[tp];
                 colors[tp] = temp;
                 tp--;
             }
         }
        return colors;
    }


    public static List<List<Integer>> threeSum (int[] nums)
    {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int pivot = 0; pivot < nums.length; pivot++) {
            if (nums[pivot] > 0) {
                break;
            }
            if (pivot > 0 && nums[pivot] == nums[pivot - 1]) {
                continue;
            }
            int low = pivot + 1, high = n - 1;
            while (low < high) {
                int tripSum = nums[pivot] + nums[low] + nums[high];
                if (tripSum > 0) {
                    high--;
                }
                else if (tripSum < 0) {
                    low++;
                }
                else {
                    result.add(Arrays.asList(nums[pivot],
                                             nums[low],
                                             nums[high]));
                    low++;
                    high--;
                    while (low < high && nums[low] == nums[low - 1]) {
                        low++;
                    }
                    while (low < high && nums[high] == nums[high + 1]) {
                        high--;
                    }
                }
            }
        }
        return result;
    }

    public static boolean validWordAbbreviation (String word,
                                                 String abbr)
    {
        int wordIndex = 0, abbrIndex = 0;
        while (abbrIndex < abbr.length()) {
            if (Character.isDigit(abbr.charAt(abbrIndex))) {
                if (abbr.charAt(abbrIndex) == '0') {
                    return false;
                }
                int num = 0;
                while (abbrIndex < abbr.length() && Character.isDigit(abbr.charAt(abbrIndex))) {
                    num = num * 10 + (abbr.charAt(abbrIndex) - '0');
                    abbrIndex++;
                }
                wordIndex += num;
            }
            else {
                if (wordIndex > word.length() || word.charAt(wordIndex) != abbr.charAt(abbrIndex)) {
                    return false;
                }
                wordIndex++;
                abbrIndex++;
            }
        }
        return wordIndex == word.length() && abbrIndex == abbr.length();
    }

    public static String longestDiverseString(int a,  int b, int c)
    {
        StringBuilder result = new StringBuilder();
        PriorityQueue<Pair<Integer, Character>> pq = new PriorityQueue<>((x,y) -> Integer.compare(y.getKey(), x.getKey()));
        if(a > 0) pq.add(new Pair<>(a, 'a'));
        if(b > 0) pq.add(new Pair<>(b, 'b'));
        if(c > 0) pq.add(new Pair<>(c, 'c'));
        int len = 0;

        while(!pq.isEmpty()) {
            // get the most frequent element
            Pair<Integer, Character> top = pq.poll();
            if(len >= 2 && result.charAt(len - 1) == top.getValue() && result.charAt(len - 2) == top.getValue()) {
                // if the three consecutive element constraint is violated
                if(!pq.isEmpty()) {
                    Pair<Integer, Character> secondTop = pq.poll();
                    Integer frequency = secondTop.getKey();
                    Character value = secondTop.getValue();
                    result.append(value);
                    if(frequency - 1 > 0)
                        pq.add(new Pair(frequency - 1, value));
                    pq.add(top);
                }
            } else {
                Integer frequency = top.getKey();
                Character value = top.getValue();
                result.append(value);
                if(frequency - 1 > 0)
                    pq.add(new Pair(frequency - 1, value));
            }
            len = result.length();
        }
        return result.toString();
    } 
}
