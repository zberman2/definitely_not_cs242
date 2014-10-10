# The Metro class defines metros located in the vertices of
# the Graph for CSAir's map
class Metro
  # readers allow us to read, but not write, to the attributes of a metro
  attr_accessor :code
  attr_accessor :name
  attr_accessor :country
  attr_accessor :continent
  attr_accessor :timezone
  attr_accessor :coordinates
  attr_accessor :population
  attr_accessor :region

  # constructor for a metro
  # Each metro stores information regarding its name and location,
  # all of which are located in the single parameter which is an
  # array of all the information necessary to define a city
  # @param metro array of information regarding the Metro
  def initialize(metro)
    @code = metro['code']
    @name = metro['name']
    @country = metro['country']
    @continent = metro['continent']
    @timezone = metro['timezone']
    # coordinates is a hash with 2 keys, each a cardinal direction
    @coordinates = metro['coordinates']
    @population = metro['population']
    @region = metro['region']
  end

  # method which prints out information located in the Metro object
  # @return string containing metro info
  def info
    info = "Code: #{@code}\n"
    info << "Name: #{@name}\n"
    info << "Country: #{@country}\n"
    info << "Continent: #{@continent}\n"
    info << "Timezone: #{@timezone}\n"

    # coordinates will have two values, so we will
    # print them out with the .each method
    coordinates_string = 'Coordinates:'
    @coordinates.each do |direction, value|
      coordinates_string << " #{direction}: #{value}"
    end
    info << coordinates_string << "\n"
    info << "Population: #{@population}\n"
    info << "Region: #{@region}"
  end
end