# file containing the Text-Based User Interface
# for Querying data from CSAir

require_relative 'Graph/init'
include Init
require_relative 'Graph/prompt'
include Prompt
require_relative 'Graph/stats'
include Stats

# create a constant MAP with info from map_data.json that
# we will use for the entire run of this program
file = 'JSON/map_data_directed.json'
# reformat map_data.json to have directed edges, and place it in file
formalize_map_data(file)
# set up our map from the new json file
MAP = set_up_map(file)

#  welcome message, only prints once
puts 'Welcome to CSAir. What would you like to know about our airline?'

quit = false
# loop until user opts to quit the program
until quit do
  input = prompt_io

  case input
    when 'q'               then quit = true
    when 'list cities'     then puts MAP.list_metros
    when 'longest flight'  then puts longest_flight(MAP)
    when 'shortest flight' then puts shortest_flight(MAP)
    when 'average flight'  then puts average_flight(MAP)
    when 'biggest city'    then puts biggest_metro(MAP)
    when 'smallest city'   then puts smallest_metro(MAP)
    when 'average city'    then puts average_metro(MAP)
    when 'list continents' then puts MAP.list_continents
    when 'hub cities'      then puts hub_metros(MAP)
    when 'visualize'       then      MAP.visualize
    when 'edit'            then      MAP.edit
    when 'save'            then      write_to_file(MAP, prompt_save_file)
    when 'route info'      then      MAP.route_info
    when 'shortest path'   then      shortest_path(MAP)
    else                        puts MAP.metro_info(input)
  end
end

puts 'Thank you and goodbye'
