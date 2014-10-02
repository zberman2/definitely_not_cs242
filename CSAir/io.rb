# file containing the Text-Based User Interface
# for Querying data from CSAir

require_relative 'Graph/init'
include Init

# create a constant MAP with info from map_data.json that
# we will use for the entire run of this program
MAP = Init.set_up_map('Graph/map_data.json')

quit = false
# loop until user opts to quit the program
until quit do
  puts
  puts 'Enter "List Cities" to see all cities served by CSAir'
  puts 'Enter a city name to see its information'
  puts 'Enter "Longest Flight" to see the longest flight'
  puts 'Enter "Shortest Flight" to see the shortest flight'
  puts 'Enter "Average Flight" to see the average flight distance'
  puts 'Enter "Biggest City" to see the biggest city served by CSAir'
  puts 'Enter "Smallest City" to see the smallest city served by CSAir'
  puts 'Enter "Average City" to see the average population of cities served by CSAir'
  puts 'Enter "List Continents" to see the continents served by CSAir'
  puts 'Enter "Hub Cities" to see the major hubs within CSAir'
  puts 'Enter "Visualize" to view CSAir\'s route map'
  puts 'Enter "Q" to quit'
  input = gets.chomp.downcase

  case input
    when 'q'               then quit = true
    when 'list cities'     then puts MAP.list_metros
    when 'longest flight'  then puts MAP.longest_flight
    when 'shortest flight' then puts MAP.shortest_flight
    when 'average flight'  then puts MAP.average_flight
    when 'biggest city'    then puts MAP.biggest_metro
    when 'smallest city'   then puts MAP.smallest_metro
    when 'average city'    then puts MAP.average_metro
    when 'list continents' then puts MAP.list_continents
    when 'hub cities'      then puts MAP.hub_metros
    when 'visualize'       then      MAP.visualize
    else                        puts MAP.metro_info(input)
  end
end
