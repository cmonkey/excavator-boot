local key = KEYS[1]
local value = redis.call("incr", key)
local ttl = redis.call("ttl", key)

local expire = ARGV[1]
local times = ARGV[2]

redis.log(redis.LOG_DEBUG,tostring(times))
redis.log(redis.LOG_DEBUG,tostring(expire))

redis.log(redis.LOG_NOTICE, "incr "..key.." "..value);

if value == 1 then 
    redis.call("expire", key, tonumber(expire))
else
    if ttl == -1 then
        redis.call("expire", key, tonumber(expire))
    end
end

if value > tonumber(times) then
    return 0
end

return 1
