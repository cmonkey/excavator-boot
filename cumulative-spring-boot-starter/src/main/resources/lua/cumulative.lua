--RedisCumulative
--KEY[1] day KEY ; ARG[1] mode : 0 day, 1 month, 2 day and month
--KEY[2] month KEY ; ARG[2] nil
--hash key KEY[2]..last  , hash value ARG[2]..last 

if #KEYS ~= #ARGV then
	return nil
end

if #KEYS == 0 then
	return nil
end

for k,v in ipairs(KEYS) do
	redis.log(redis.LOG_WARNING,"----mode is"..ARGV[1]..", key is "..KEYS[1]..", hash key is "..KEYS[k]..", hash value is "..ARGV[k])
	if k >= 3 then
		-- day
		--if tonumber(ARGV[1]) == 0 or tonumber(ARGV[1]) == 2 then
		if tonumber(ARGV[1]) == 0 then
			redis.call('HINCRBY',KEYS[1],KEYS[k],ARGV[k])
	    	--redis.log(redis.LOG_WARNING,"mode is"..ARGV[1]..", key is "..KEYS[1]..", hash key is "..KEYS[k]..", hash value is "..ARGV[k])
	    end
	    -- month
	    -- if tonumber(ARGV[1]) == 1 or tonumber(ARGV[1]) == 2 then
		if tonumber(ARGV[1]) == 1 then
	    	redis.call('HINCRBY',KEYS[1],KEYS[k],ARGV[k])
	    	--redis.log(redis.LOG_WARNING,"mode is"..ARGV[1]..", key is "..KEYS[2]..", hash key is "..KEYS[k]..", hash value is "..ARGV[k])
	    end

		-- dayAndMonth
        if tonumber(ARGV[1]) == 2 then
            redis.call("HINCRBY", KEYS[1], KEYS[k], ARGV[k])
			redis.call("HINCRBY", KEYS[2], KEYS[k], ARGV[k])
        end

		-- year
		if tonumber(ARGV[1]) == 3 then
			redis.call("HINCRBY", KEYS[1], KEYS[k], ARGV[k])
		end


	end
end

return 0