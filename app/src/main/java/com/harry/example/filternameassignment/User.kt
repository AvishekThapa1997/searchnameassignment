package com.harry.example.filternameassignment

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("name ")val name : String,@SerializedName("userId")val id : Int)