1 //方法一
 2 var arr = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 3 function removeDuplicatedItem(arr) {
 4    for(var i = 0; i < arr.length-1; i++){
 5        for(var j = i+1; j < arr.length; j++){
 6            if(arr[i]==arr[j]){
 7
 8              arr.splice(j,1);//console.log(arr[j]);
 9               j--;
10            }
11        }
12    }
13    return arr;
14 }
15 
16 arr2 = removeDuplicatedItem(arr);
17 console.log(arr);
18 console.log(arr2);




//方法二
 2 //借助indexOf()方法判断此元素在该数组中首次出现的位置下标与循环的下标是否相等
 3 var ar = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 4 function rep2(arr) {
 5     for (var i = 0; i < arr.length; i++) {
 6         if (arr.indexOf(arr[i]) != i) {
 7             arr.splice(i,1);//删除数组元素后数组长度减1后面的元素前移
 8             i--;//数组下标回退
 9         }
10     }
11     return arr;
12 }
13 var a1 = rep2(ar);
14 console.log(ar);
15 console.log(a1);



 //方法三  利用数组中的filter方法
2  var arr = ['apple','strawberry','banana','pear','apple','orange','orange','strawberry'];
3  var r = arr.filter(function(element,index,self){
4     return self.indexOf(element) === index;
5  });
6  console.log(r);



 1 //方法四  借助新数组 通过indexOf方判断当前元素在数组中的索引如果与循环的下标相等则添加到新数组中
 2 var arr = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 3 function rep(arr) {
 4     var ret = [];
 5     for (var i = 0; i < arr.length; i++) {
 6         if (arr.indexOf(arr[i]) == i) {
 7             ret.push(arr[i]);
 8         }
 9     }
10     return ret;
11 }
12 arr2 = rep(arr);
13 console.log(arr);
14 console.log(arr2);



 1 //方法五 利用空对象来记录新数组中已经存储过的元素
 2 var arr = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 3 var o={};
 4 var new_arr=[];
 5 for(var i=0;i<arr.length;i++){
 6     var k=arr[i];
 7     if(!o[k]){
 8         o[k]=true;
 9         new_arr.push(k);
10     }
11 }
12 console.log(new_arr);
复制代码
复制代码
 1 //方法六 借助新数组  判断新数组中是否存在该元素如果不存在则将此元素添加到新数组中
 2 var arr = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 3 Array.prototype.reArr = function(){
 4     var newArr = [];
 5     for(var i = 0; i < this.length; i++){
 6         if(newArr.indexOf(this[i])== -1){
 7             newArr.push(this[i]);
 8         }
 9     }
10     return newArr;
11 }
12 var arr2 = arr.reArr();
13 console.log(arr); //[ 1, 23, 1, 1, 1, 3, 23, 5, 6, 7, 9, 9, 8, 5 ]
14 console.log(arr2);//[ 1, 23, 3, 5, 6, 7, 9, 8 ]
复制代码
复制代码
 1 //方法七(原数组长度不变但被按字符串顺序排序) 借助新数组  判断新数组中是否存在该元素如果不存在则将此元素添加到新数组中
 2 var arr = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 3 function removeRepEle(ar) {
 4     var ret = [],
 5         end;//临时变量用于对比重复元素
 6     ar.sort();//将数重新组排序
 7     end = ar[0];
 8     ret.push(ar[0]);
 9     for (var i = 1; i < ar.length; i++) {
10         if (ar[i] != end) {//当前元素如果和临时元素不等则将此元素添加到新数组中
11             ret.push(ar[i]);
12             end = ar[i];
13         }
14     }
15     return ret;
16 }
17 
18 arr2 = removeRepEle(arr);
19 console.log(arr);//[ 1, 1, 1, 1, 23, 23, 3, 5, 5, 6, 7, 8, 9, 9 ]
20 console.log(arr2);//[ 1, 23, 3, 5, 6, 7, 8, 9 ]
复制代码
复制代码
 1 //方法八(此方法没有借助新数组直接改变原数组,并且去重后的数组被排序)
 2 var arr = [1,23,1,1,1,3,23,5,6,7,9,9,8,5];
 3 function removeRepEle(ar) {
 4     var  end;//临时变量用于对比重复元素
 5     ar.sort();//将数重新组排序
 6     end = ar[0];
 7     for (var i = 1; i < ar.length; i++) {
 8         if (ar[i] == end) {//当前元素如果和临时元素相等则将此元素从数组中删除
 9             ar.splice(i,1);
10             i--;
11         }else{
12             end = ar[i];
13         }
14     }
15     return ar;
16 }
17 arr2 = removeRepEle(arr);
18 console.log(arr); //[ 1, 23, 3, 5, 6, 7, 8, 9 ]
19 console.log(arr2);//[ 1, 23, 3, 5, 6, 7, 8, 9 ]


 1 //方法九(双层循环改变原数组)
 2 var arr = [1, 1, 1, 3, 4, 4, 4, 5, 5, 5, 5, 4, 6];
 3 function removeArrayRepElement(arr){
 4     for (var i = 0; i < arr.length; i++) {
 5         for (var j = 0; j < arr.length; j++) {
 6             if (arr[i] == arr[j] && i != j) {//将后面重复的数删掉
 7                 arr.splice(j, 1);
 8             }
 9         }
10     }
11     return arr;
12 }
13 var arr2  = removeArrayRepElement(arr);
14 console.log(arr); //[ 1, 3, 4, 5, 6 ]
15 console.log(arr2);//[ 1, 3, 4, 5, 6 ]
复制代码
复制代码
//方法十(借助新数组)
var arr = [12, 2, 44, 3, 2, 32, 33, -2, 45, 33, 32, 3, 12];
var newArr = [];
for (var i = 0; i < arr.length; i++) {
    var repArr = [];//接收重复数据后面的下标
    //内层循环找出有重复数据的下标
    for (var j = i + 1; j < arr.length; j++) {
        if (arr[i] == arr[j]) {
            repArr.push(j);//找出后面重复数据的下标
        }
    }
    //console.log(repArr);
    if (repArr.length == 0) {//若重复数组没有值说明其不是重复数据
        newArr.push(arr[i]);
    }
}
console.log(newArr);//[ 44, 2, -2, 45, 33, 32, 3, 12 ]