# module containing various prompts for the text based UI
module Prompt
  require_relative 'edge'
  require_relative 'graph'
  require_relative 'metro'
  require_relative 'vertex'

  private

  # confirms that the input is a valid latitude (i.e. N 30)
  # @return true if it is
  def is_valid_latitude(direction, coordinate)
    (direction == 'N' || direction == 'S') &&
        coordinate >= 0 && coordinate <= 90
  end

  # confirms that the input is a valid longitude (i.e. W 120)
  # @return true if it is
  def is_valid_longitude(direction, coordinate)
    (direction == 'W' || direction == 'E') &&
        coordinate >= 0 && coordinate <= 180
  end

  public

  # main prompt for text based UI
  # @return user input
  def prompt_io
    puts # blank line to separate prompts from results
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
    puts 'Enter "Edit" to make changes to the CSAir network'
    puts 'Enter "Route Info" to see details of a particular route'
    puts 'Enter "Shortest Path" to see the shortest path between cities'
    puts 'Enter "Save" to write network to disk'
    puts 'Enter "Q" to quit'
    gets.chomp.downcase
  end

  # prompts the user to save the current map
  # @return file name for save location
  def prompt_save_file
    f_name_start = 'JSON/'
    # ensure file ends with .json
    f_name_end = '.json'
    print 'Enter file name: '
    f_name_start << gets.chomp << f_name_end
  end

  # prompts the user to enter a 3 letter metro code
  # @return code
  def prompt_code
    code = ''
    # loop until code is a valid length
    until code.length == 3
      print 'Enter 3 letter city code: '
      code = gets.chomp.upcase
    end
    code
  end

  # prompts user to enter a metro name
  # @return name
  def prompt_name
    name = ''
    # loop until metro name is not empty
    until name.length > 0
      print 'Enter the city\'s name: '
      name = gets.chomp
    end
    name
  end

  # prompts user to enter a country code
  # @return country
  def prompt_country
    country = ''
    # loop until country length is 2 letters
    until country.length == 2
      print 'Enter 2 letter country code: '
      country = gets.chomp.upcase
    end
    country
  end

  # prompts user to enter a continent
  # @return continent
  def prompt_continent
    continent = ''
    # loop until continent is not empty
    until continent.length > 0
      print 'Enter the continent\'s name: '
      continent = gets.chomp.capitalize
    end
    continent
  end

  # prompts user to enter a timezone number
  # @return timezone
  def prompt_timezone
    timezone = 13
    # loop until the number entered is a valid timezone code
    until timezone >= -12 && timezone <= 12
      print 'Enter the number corresponding to your city\'s timezone: '
      timezone = gets.chomp.to_f
    end
    timezone
  end

  # prompts the user to enter longitude and latitude coordinates
  # @return hash corresponding to the coordinates
  def prompt_coordinates
    coordinates = Hash.new
    direction = ''
    coordinate = -1
    until is_valid_latitude(direction, coordinate)
      print 'Enter latitude ("N" or "S"): '
      direction = gets.chomp.upcase
      print 'Enter latitude value: '
      coordinate = gets.chomp.to_i
    end
    # add latitude
    coordinates[direction] = coordinate

    direction = ''
    coordinate = -1
    until is_valid_longitude(direction, coordinate)
      print 'Enter longitude ("E" or "W"): '
      direction = gets.chomp.upcase
      print 'Enter longitude value: '
      coordinate = gets.chomp.to_i
    end
    # add longitude
    coordinates[direction] = coordinate
    coordinates
  end

  # prompt user to enter metro population
  # @return population
  def prompt_population
    population = 0
    until population > 0
      print 'Enter the city\'s population: '
      population = gets.chomp.to_i
    end
    population
  end

  # prompt user to enter a region number
  # @return region
  def prompt_region
    region = -1
    until region >= 1 && region <= 4
      print 'Enter the city\'s region number: '
      region = gets.chomp.to_i
    end
    region
  end

  # prompt user to enter values for a user created metro
  # @return hash with all of the metro's info
  def prompt_metro_info
    metro_hash = Hash.new
    metro_hash['code'] = prompt_code
    metro_hash['name'] = prompt_name
    metro_hash['country'] = prompt_country
    metro_hash['continent'] = prompt_continent
    metro_hash['timezone'] = prompt_timezone
    metro_hash['coordinates'] = prompt_coordinates
    metro_hash['population'] = prompt_population
    metro_hash['region'] = prompt_region
    metro_hash
  end

  # prompt user to enter a metro name for use in graph searches
  # @return downcase metro name
  def prompt_metro
    metro = ''
    until metro.length > 0
      print 'Enter city name: '
      metro = gets.chomp.downcase
    end
    metro
  end

  # prompt for editing a metro's information
  # @return user input
  def prompt_edit_metro
    puts 'Enter "Code" to change the city\'s code'
    puts 'Enter "Name" to change the city\'s name'
    puts 'Enter "Country" to change the city\'s country'
    puts 'Enter "Continent" to change the city\'s continent'
    puts 'Enter "Timezone" to change the city\'s timezone'
    puts 'Enter "Coordinates" to change the city\'s coordinates'
    puts 'Enter "Population" to change the city\'s population'
    puts 'Enter "Region" to change the city\'s region'
    puts 'Enter "B" to quit'
    gets.chomp.downcase
  end

  # prompt for editing the CSAir network
  # @return user input
  def prompt_edit
    puts 'Enter "Remove City" to delete a city from the network'
    puts 'Enter "Remove Route" to delete a route from the network'
    puts 'Enter "Add City" to add a city to the network'
    puts 'Enter "Add Route" to add a route to the network'
    puts 'Enter "Edit City" to make changes to a city in the network'
    puts 'Enter "B" to go back'
    gets.chomp.downcase
  end

  # prompt user to enter 2 metros corresponding to a start/end point
  # @return array with 2 downcase metro names
  def prompt_route
    ports = Array.new

    # loop until metro name is not empty
    port_start = ''
    until port_start.length > 0
      print 'Enter start city name: '
      port_start = gets.chomp.downcase
    end
    ports << port_start

    # loop until second name is not empty and not equal to the first name
    port_end = ''
    until port_end.length > 0 && port_end != port_start
      print 'Enter end city name: '
      port_end = gets.chomp.downcase
    end
    ports << port_end
    ports
  end

  # prompt the user to enter an additional route when accumulating
  # a list of metros
  # @return downcase metro name
  def prompt_route_list(count)
    port = ''
    until port.length > 0
      print "Enter city \##{count+1} "
      print 'or "D" if route is complete ' if count >= 2
      port = gets.chomp.downcase
    end
    port
  end

  # prompt the user to enter a distance value for a new edge
  # @return distance
  def prompt_distance
    distance = 0
    # loop until distance is bigger than 0
    until distance > 0
      print 'Enter distance in km: '
      distance = gets.chomp.to_i
    end
    distance
  end
end